package tech.execsuroot.jarticle.particle;

import org.bukkit.Location;

import java.util.List;

public class AnimationPlayer {

    private final List<FramePlayer> frames;
    private int repeatLeft;
    private int tick = 0;

    public AnimationPlayer(AnimationData data) {
        this.frames = data.getFrames().stream().map(FramePlayer::new).toList();
        if (data.getRepeat() == AnimationData.INFINITE_REPEAT) {
            this.repeatLeft = Integer.MAX_VALUE;
        } else {
            this.repeatLeft = data.getRepeat();
        }
    }

    public void playTick(Location location) {
        if (repeatLeft == 0) return;
        tick++;
        boolean hasEnded = true;
        for (FramePlayer frame : frames) {
            if (frame.hasEnded(tick)) continue;
            frame.playTick(location, tick);
            hasEnded = false;
        }
        if (hasEnded) {
            repeatLeft--;
            tick = 0;
        }
    }
}
