class Main {

    public static void main(String[] args) {
        
        System.out.println("Default Playback (static): " + Controls.getPlayback());

        Video video = new Video("1080p");
        Audio audio = new Audio("wav");

        video.play();
        video.pause();
        System.out.println(video.getPlayback());
        System.out.println(video.getResolution());

        audio.play();
        System.out.println(audio.getPlayback());
        audio.pause();
        System.out.println(audio.getFileType());

        
    }

}

class Controls{

    private static boolean playback = false;

    public static boolean getPlayback() {
        return playback;
    }
    
}

class Media {

    protected boolean playback = false;

    void play() {
        playback = true;
    }

    void pause() {
        playback = false;
    }

}

class Audio extends Media {

    private String fileType;

    Audio(String fileType) {

        this.fileType = fileType;

    } 

    String getFileType() {
        return fileType;
    }

    boolean getPlayback() {
        return super.playback;
    }

}

class Video extends Media {

    private String resolution;

    Video(String resolution) {

        this.resolution = resolution;

    } 

    String getResolution() {
        return resolution;
    }

    boolean getPlayback() {
        return super.playback;
    }

}