package Project3_136.MainClass;

public class MySoundEffectControl {
    public String soundPath = "src/main/java/Project3_136/resources/SoundEffect/";
    public String ThemeSongPath = "src/main/java/Project3_136/resources/ThemeSong/";
    private float MusicVolume = -15f, SoundVolume = -15f;
    private boolean MusicMute = false, SoundMute = false;
    public MySoundEffect CoinSFX = new MySoundEffect(soundPath + "coin.wav");
    public MySoundEffect BreakSFX = new MySoundEffect(soundPath + "knife.wav");
    public MySoundEffect ThemeSongBackGround = new MySoundEffect(ThemeSongPath + "GameThemeSong.wav");
    public MySoundEffect SoundClick = new MySoundEffect(soundPath + "Clicked.wav");
    public MySoundEffectControl() { }
    public void playMusic() {
        ThemeSongBackGround.setVolume(MusicVolume);
        ThemeSongBackGround.playLoop();
    }
    public void changeMusicVolume(float volume) {
        MusicVolume = volume;
        ThemeSongBackGround.setVolume(volume);
    }
    public void changeSoundVolume(float volume) {
        SoundVolume = volume;
    }
    public void playSoundEffect(MySoundEffect SoundEffect) {
        if(!SoundMute) {
            SoundEffect.setVolume(SoundVolume);
            SoundEffect.playOnce();
        }
        else {
            SoundEffect.stop();
        }
    }
    public void setMusicMute(boolean MusicMute) {
        System.out.println("Setting Song Mute: " + MusicMute);
        this.MusicMute = MusicMute;

        if(MusicMute) {
            System.out.println("Song Muted");
            ThemeSongBackGround.stop();
        } else {
            System.out.println("Song Unmuted");
            ThemeSongBackGround.playLoop();
        }
    }
    public void setSoundMute(boolean SoundMute) {
        this.SoundMute = SoundMute;
    }
    public boolean getSoundMute() { return SoundMute; }
    public boolean getMusicMute() {
        return MusicMute;
    }
    public float getMusicVolume(){
        return MusicVolume;
    }
    public float getSoundVolume(){
        return SoundVolume;
    }
}
