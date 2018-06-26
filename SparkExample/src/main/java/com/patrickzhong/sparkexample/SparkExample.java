package com.patrickzhong.sparkexample;

import com.patrickzhong.spark.SparkPlugin;
import com.patrickzhong.sparkexample.command.EchoCommand;
import com.patrickzhong.sparkexample.command.SpongeCommand;
import lombok.Getter;

public class SparkExample extends SparkPlugin {

    @Getter private static SparkExample instance;

    @Override
    public void onEnable() {

        instance = this;
        new EchoCommand().register(this);
        new SpongeCommand().register(this);

        super.onEnable();

    }

}
