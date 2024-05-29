package com.metacontent.cobblenav.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.metacontent.cobblenav.Cobblenav;
import com.metacontent.cobblenav.config.util.MainScreenWidgetType;
import com.metacontent.cobblenav.config.util.PercentageDisplayType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Map;

public class ClientCobblenavConfig {
    public final float screenScale;
    public final MainScreenWidgetType mainScreenWidget;
    public final PercentageDisplayType percentageDisplayType;
    public final boolean bucketWisePercentageCalculation;
    public final int reverseSortingButtonCooldown;
    public final int scrollSize;
    public final boolean notifyIfPokemonIsNotFound;
    public final int trackArrowVerticalOffset;
    public final Map<String, Double> partyWidgetAdjustments;

    private ClientCobblenavConfig(
            float screenScale,
            MainScreenWidgetType mainScreenWidget,
            PercentageDisplayType percentageDisplayType,
            boolean bucketWisePercentageCalculation,
            int reverseSortingButtonCooldown,
            int scrollSize,
            boolean notifyIfPokemonIsNotFound,
            int trackArrowVerticalOffset,
            Map<String, Double> partyWidgetAdjustments
    ) {
        this.screenScale = screenScale;
        this.mainScreenWidget = mainScreenWidget;
        this.percentageDisplayType = percentageDisplayType;
        this.bucketWisePercentageCalculation = bucketWisePercentageCalculation;
        this.reverseSortingButtonCooldown = reverseSortingButtonCooldown;
        this.scrollSize = scrollSize;
        this.notifyIfPokemonIsNotFound = notifyIfPokemonIsNotFound;
        this.trackArrowVerticalOffset = trackArrowVerticalOffset;
        this.partyWidgetAdjustments = partyWidgetAdjustments;
    }

    private ClientCobblenavConfig() {
        this(1f, MainScreenWidgetType.PARTY, PercentageDisplayType.PERCENT_ONLY, false, 100, 20, true, 70, Map.of());
    }

    public static ClientCobblenavConfig init() {
        Cobblenav.LOGGER.info("Initializing client " + Cobblenav.ID + " config");
        ClientCobblenavConfig config;
        Gson gson = (new GsonBuilder()).disableHtmlEscaping().setPrettyPrinting().create();
        File file = new File(FabricLoader.getInstance().getConfigDir() + Cobblenav.CLIENT_CONFIG_PATH);
        file.getParentFile().mkdirs();
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file)) {
                config = gson.fromJson(fileReader, ClientCobblenavConfig.class);
            }
            catch (Throwable throwable) {
                Cobblenav.LOGGER.error(throwable.getMessage(), throwable);
                config = new ClientCobblenavConfig();
            }
        }
        else {
            config = new ClientCobblenavConfig();
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            gson.toJson(config, printWriter);
        }
        catch (Throwable throwable) {
            Cobblenav.LOGGER.error(throwable.getMessage(), throwable);
        }

        return config;
    }
}
