package tech.execsuroot.jarticle.particle;

import lombok.NonNull;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.Optional;

public class ParticlePlayer {

    private final ParticleData data;
    private final Object particleData;

    public ParticlePlayer(ParticleData data) {
        this.data = data;
        this.particleData = Optional.of(data.getData())
                .map((string) -> stringToData(string, data.getType().getDataType()))
                .orElse(null);
    }

    public void play(Location location) {
        Vector offset = data.getOffset();
        location.getWorld().spawnParticle(
                data.getType(),
                location.clone().add(data.getPosition()),
                data.getAmount(),
                offset.getX(), offset.getY(), offset.getZ(),
                data.getSpeed(),
                particleData
        );
    }

    private Object stringToData(@NonNull String data, @NonNull Class<?> dataClass) {
        if (dataClass == Integer.class) {
            return Integer.parseInt(data);
        } else if (dataClass == Float.class) {
            return Float.parseFloat(data);
        } else if (dataClass == Boolean.class) {
            return Boolean.parseBoolean(data);
        } else if (dataClass == Particle.DustOptions.class) {
            String[] split = data.replace(" ", "").split(",");
            Color color = Color.fromRGB(
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2])
            );
            return new Particle.DustOptions(color, Float.parseFloat(split[3]));
        }
        return null;
    }
}
