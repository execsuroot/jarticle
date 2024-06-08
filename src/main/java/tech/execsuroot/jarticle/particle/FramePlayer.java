package tech.execsuroot.jarticle.particle;

import org.bukkit.Location;

import java.util.List;

public class FramePlayer {

    private final List<ParticlePlayer> particles;
    private final int startOfFrameTick;
    private final int endOfFrameTick;

    public FramePlayer(FrameData data) {
        this.particles = data.getParticles().stream().map(ParticlePlayer::new).toList();
        this.startOfFrameTick = data.getDelay();
        if (data.getDuration() == FrameData.INFINITE_DURATION) {
            this.endOfFrameTick = Integer.MAX_VALUE;
        } else {
            this.endOfFrameTick = data.getDelay() + data.getDuration();
        }
    }

    public void playTick(Location location, int tick) {
        if (tick < startOfFrameTick || tick > endOfFrameTick) return;
        particles.forEach(particle -> particle.playTick(location));
    }

    public boolean hasEnded(int tick) {
        return tick > endOfFrameTick;
    }
}
