package Mp3Player;
import View.GameBuilder.Title;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import de.hsrm.mi.eibo.simpleplayer.MinimHelper;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Mp3Player{
	public Minim minim ;
	public AudioPlayer player;
	boolean manuelStoped = true;
	public SimpleDoubleProperty currentTime;
	public SimpleDoubleProperty currentVolme;
	public SimpleBooleanProperty ended;
	public Title currentTitle;
	boolean stoped = true;
	
	public Mp3Player() {
		ended = new SimpleBooleanProperty();
		ended.set(false);
		currentVolme = new SimpleDoubleProperty();
		currentVolme.set(0.5);
		currentTime = new SimpleDoubleProperty();
		updateData();
		
	}
	public void SetVolume(float linear) {
		float dB;
		if (linear != 0)
			dB = (float) (25*Math.log10(linear));
		else
			dB = -144.0f;
         player.setGain(dB);
	}
	/**
	 * play Methode die das geladen Lied Spielt;
	 */
	public void play() {
		Thread playThread = new Thread() {
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						player.play();
						stoped = false;
					}
				});
			}		
		};		
		playThread.start();
		
	}
	
	/**
	 * pausiert das Lied
	 */
	public void Break() {
		stoped = true;
		player.pause();
		
	}
	
	
	/**
	 * Stop methode Stop das Lied und Spult zu anfang zurück
	 */
	public void Stop() {
		stoped = true;
		player.pause();
		player.rewind();
	}
	
	/**
	 * eine neue Playlist soll hinzugefügt werden
	 * @param newPlaylist die neue Playlist
	 */
	
	private void updateData() {
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							if(player!= null) {
							currentTime.set((float)player.position()/1000);
							currentVolme.set(player.getVolume());
							if(!player.isPlaying()&&!stoped) {
								ended.set(true);
							}
							}
						}	
					});
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
				
		thread.start();
		
	}
	
	public void loadSong(String FileName) {
		
		double iwas= this.currentVolme.getValue();
		currentTitle = new Title(FileName);
		minim = new Minim(new MinimHelper());
		player = minim.loadFile(FileName);
		player.setVolume((float)iwas);
		
	}
	public Title getCurrentTitle() {
		return currentTitle;
	}
	public void loop() {
		Thread thread = new Thread() {
			public void run() {
				
					Platform.runLater(new Runnable(){

						@Override
						public void run() {
							if (!player.isPlaying()&& !stoped) {
								
								player.play();
							}
						}	
					});
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
			};
		};
				
		thread.start();
	}
}