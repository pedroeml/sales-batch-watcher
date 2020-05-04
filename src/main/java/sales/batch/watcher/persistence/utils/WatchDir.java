/*
 * Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package sales.batch.watcher.persistence.utils;

import sales.batch.watcher.business.strategy.ReportProcessorStrategy;

import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.*;
import java.util.*;

public class WatchDir {
    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final ReportProcessorStrategy strategy;

    public WatchDir(Path dir, ReportProcessorStrategy strategy) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<>();
        this.strategy = strategy;
        this.register(dir);
    }

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        Path prev = keys.get(key);

        if (prev == null) {
            System.out.format("register: %s\n", dir);
        } else if (!dir.equals(prev)) {
            System.out.format("update: %s -> %s\n", prev, dir);
        }

        keys.put(key, dir);
    }

    public void processQueuedEvents() {
        System.out.println("Waiting for directory changes...");

        for (;;) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                break;
            }

            Path dir = keys.get(key);
            if (dir != null) {
                pollEvents(key, dir);

                if (removeKeyFromSet(key)) {
                    break;
                }
            }
        }
    }

    private void pollEvents(WatchKey key, Path dir) {
        for (WatchEvent<?> event: key.pollEvents()) {
            WatchEvent.Kind kind = event.kind();

            if (kind == ENTRY_MODIFY) {
                this.handleEvent(event, dir);
            }
        }
    }

    private void handleEvent(WatchEvent<?> event, Path dir) {
        WatchEvent<Path> ev = cast(event);
        Path name = ev.context();
        Path child = dir.resolve(name);

        this.strategy.processFile(child.toString());
    }

    private boolean removeKeyFromSet(WatchKey key) {
        boolean valid = key.reset();
        boolean isAllDirsInaccesible = false;

        if (!valid) {
            this.keys.remove(key);
            isAllDirsInaccesible = this.keys.isEmpty();
        }

        return isAllDirsInaccesible;
    }
}
