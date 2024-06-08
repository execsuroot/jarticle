package tech.execsuroot.jarticle.particle;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.Optional;

@RequiredArgsConstructor
public class ParticlePlayer {

    private final ParticleData data;

    public void playTick(Location location) {
        Vector offset = data.getOffset();
        location.getWorld().spawnParticle(
                data.getType(),
                location.clone().add(data.getPosition()),
                data.getAmount(),
                offset.getX(), offset.getY(), offset.getZ(),
                data.getSpeed(),
                Optional.of(data.getData())
                        .map((string) -> stringToData(string, data.getType().getDataType()))
                        .orElse(null)
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
