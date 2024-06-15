package tech.execsuroot.jarticle.particle;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class AnimationPlayer {

    private final List<FramePlayer> relativeFrames;
    private final List<FramePlayer> absoluteFrames;
    private int repeat;
    private int frameIndex = 0;

    public AnimationPlayer(AnimationData data) {
        this.repeat = data.getRepeat();
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
        FramePlayer relativeFrame = relativeFrames.get(frameIndex);
        boolean shouldContinuePlayingFrame = relativeFrame.playTick(location);
        for (FramePlayer absoluteFrame : absoluteFrames) {
            absoluteFrame.playTick(location);
        }
        if (!shouldContinuePlayingFrame) {
            boolean isLastFrame = relativeFrame == relativeFrames.getLast();
            if (isLastFrame) {
                frameIndex = 0;
                if (repeat != AnimationData.INFINITE_REPEAT) repeat--;
                reset();
            } else frameIndex++;
        }
        return repeat == AnimationData.INFINITE_REPEAT || repeat > 0;
    }

    private void reset() {
        for (FramePlayer relativeFrame : relativeFrames) relativeFrame.reset();
        for (FramePlayer absoluteFrame : absoluteFrames) absoluteFrame.reset();
    }
}
