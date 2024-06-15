package tech.execsuroot.jarticle.particle;

import org.bukkit.Location;

import java.util.List;

public class FramePlayer {

    private final List<ParticlePlayer> particles;
    private final int startOfFrameTick;
    private final int endOfFrameTick;
    private int tick = 0;

    public FramePlayer(FrameData data) {
        this.particles = data.getParticles().stream().map(ParticlePlayer::new).toList();
        this.startOfFrameTick = data.getDelay();
        if (data.getDuration() == FrameData.INFINITE_DURATION) {
            this.endOfFrameTick = Integer.MAX_VALUE;
        } else {
            this.endOfFrameTick = data.getDelay() + data.getDuration();
        }
    }

    /**
     * Plays a tick of the frame at the given location.
     * @return {@code true} if should continue playing, {@code false} if the frame is done.
     */
    public boolean playTick(Location location) {
        tick++;
        if (tick < startOfFrameTick) return true;
        if (tick > endOfFrameTick) return false;
        for (ParticlePlayer particle : particles) {
            particle.play(location);
        }
        return tick < endOfFrameTick;
    }

    /**
     * Resets the frame's internal state.
     */
    public void reset() {
        tick = 0;
    }
}
