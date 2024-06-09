package tech.execsuroot.jarticle.particle;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class AnimationPlayer {

    private final List<FramePlayer> relativeFrames;
    private final List<FramePlayer> absoluteFrames;
    private int

    public AnimationPlayer(AnimationData data) {
        this.relativeFrames = new ArrayList<>();
        this.absoluteFrames = new ArrayList<>();
        for (FrameData frame : data.getFrames()) {
            if (frame.isAbsolute()) {
                absoluteFrames.add(new FramePlayer(frame));
            } else {
                relativeFrames.add(new FramePlayer(frame));
            }
        }
    }

    /**
     * Plays a tick of the animation at the given location.
     * @return {@code true} if should continue playing, {@code false} if the animation is done.
     */
    public boolean playTick(Location location) {
        
    }
}
