package com.mylearnings.java.designpatterns.structural;

/**
 * ADAPTER DESIGN PATTERN
 *
 * PURPOSE:
 * Convert the interface of a class (Source / Adaptee)
 * into another interface (Target) that the client expects.
 *
 * INTENT:
 * Enable incompatible interfaces to work together.
 *
 * REAL JAVA API EXAMPLES:
 * - Arrays.asList(array)           -> Array (Source) to List (Target)
 * - InputStreamReader(InputStream) -> InputStream (Source) to Reader (Target)
 * - Spring HandlerAdapter          -> Adapts different controllers
 */

// =======================
// TARGET INTERFACE
// =======================
// What the client expects to work with
interface MediaPlayer {
    void play(String fileName);
}

// =======================
// SOURCE / ADAPTEE CLASSES
// =======================
// Existing classes with incompatible interfaces

class VlcPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing VLC file: " + fileName);
    }
}

class Mp4Player {
    public void playMp4(String fileName) {
        System.out.println("Playing MP4 file: " + fileName);
    }
}

// =======================
// ADAPTER
// =======================
// Converts Source interface into Target interface
class MediaAdapter implements MediaPlayer {

    private final VlcPlayer vlcPlayer;
    private final Mp4Player mp4Player;

    public MediaAdapter(String mediaType) {

        // Adapter decides which source to adapt
        if ("vlc".equalsIgnoreCase(mediaType)) {
            this.vlcPlayer = new VlcPlayer();
            this.mp4Player = null;
        } else if ("mp4".equalsIgnoreCase(mediaType)) {
            this.mp4Player = new Mp4Player();
            this.vlcPlayer = null;
        } else {
            this.vlcPlayer = null;
            this.mp4Player = null;
        }
    }

    @Override
    public void play(String fileName) {
        if (vlcPlayer != null) {
            vlcPlayer.playVlc(fileName);   // adapting VLC → MediaPlayer
        } else if (mp4Player != null) {
            mp4Player.playMp4(fileName);   // adapting MP4 → MediaPlayer
        }
    }
}

// =======================
// CLIENT
// =======================
// Works only with Target interface
class AudioPlayer implements MediaPlayer {

    @Override
    public void play(String fileName) {

        String extension =
                fileName.substring(fileName.lastIndexOf('.') + 1);

        if ("mp3".equalsIgnoreCase(extension)) {
            // Native support (no adapter required)
            System.out.println("Playing MP3 file: " + fileName);

        } else if ("vlc".equalsIgnoreCase(extension)
                || "mp4".equalsIgnoreCase(extension)) {

            // Adapter is used only when needed
            MediaPlayer adapter = new MediaAdapter(extension);
            adapter.play(fileName);

        } else {
            System.out.println("Unsupported format: " + extension);
        }
    }
}

// =======================
// DEMO
// =======================
public class AdapterDesignPattern {

    public static void main(String[] args) {

        AudioPlayer player = new AudioPlayer();

        player.play("song.mp3");     // No adapter
        player.play("movie.mp4");    // Uses Adapter
        player.play("video.vlc");    // Uses Adapter
        player.play("doc.avi");      // Unsupported
    }
}
