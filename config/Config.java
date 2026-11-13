package com.senin.client.config;

import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.glfw.GLFW;
import java.io.*;
import java.util.Properties;

public class Config {
    // HUD
    public static boolean hud = true, keystrokes = true, cps = true, coords = true,
        armor = true, ping = true, potionHud = true, comboCounter = true;
    // PvP
    public static boolean toggleSprint = false, noHurtCam = true, fullbright = false,
        zoomEnabled = true, noFire = true;
    // Görsel
    public static boolean swordColor = true;   public static int swordRGB = 0xFF00FFFF;
    public static boolean pickaxeColor = true; public static int pickaxeRGB = 0xFFFFAA00;
    public static boolean floatingSword = true, shieldRed = true;
    public static boolean critColor = true;    public static int critRGB = 0xFFAA00FF;
    public static boolean blockOutlineColor = true; public static int outlineRGB = 0xFF00FF88;

    // Keybinds
    public static int keyMenu = GLFW.GLFW_KEY_RIGHT_SHIFT;
    public static int keyZoom = GLFW.GLFW_KEY_R;
    public static int keyFullbright = GLFW.GLFW_KEY_G;
    public static int keyNoHurtCam = GLFW.GLFW_KEY_H;
    public static int keyToggleSprint = GLFW.GLFW_KEY_F;
    public static int keyHud = GLFW.GLFW_KEY_V;

    // Target
    public static boolean targetIndicator = true;
    public static int targetRGB = 0xFFFF3333;
    public static float targetDuration = 3.0f;
    public static float comboTimeout = 3.0f;

    // ViewModel
    public static boolean viewModel = true;
    public static float vmPosX = 0f, vmPosY = 0f, vmPosZ = 0f;
    public static float vmRotX = 0f, vmRotY = 0f, vmRotZ = 0f;
    public static float vmScale = 1f;

    // Optimizasyon (FPS)
    public static boolean entityCulling = true;
    public static int cullDistance = 64;
    public static boolean noFog = true;
    public static boolean noWeather = true;
    public static boolean noClouds = false;
    public static boolean reduceParticles = true;
    public static int particleLimit = 2000;

    // Sky
    public static int skyTheme = 0;
    // Crosshair
    public static boolean customCrosshair = false;
    public static int crosshairRGB = 0xFFFFFFFF;
    public static int crosshairSize = 6;
    public static int crosshairThickness = 2;
    // Hit sound
    public static boolean hitSound = false;

    public static float[] rgb(int hex) {
        return new float[]{
            ((hex >> 16) & 0xFF) / 255f,
            ((hex >> 8) & 0xFF) / 255f,
            (hex & 0xFF) / 255f
        };
    }

    public static int[] skyColor() {
        return switch (skyTheme) {
            case 1 -> new int[]{10, 10, 40};
            case 2 -> new int[]{255, 140, 90};
            case 3 -> new int[]{120, 0, 0};
            case 4 -> new int[]{80, 0, 120};
            default -> new int[]{120, 167, 255};
        };
    }

    private static File file() {
        return FabricLoader.getInstance().getConfigDir().resolve("seninclient.properties").toFile();
    }

    public static void save() {
        Properties p = new Properties();
        p.setProperty("hud", String.valueOf(hud)); p.setProperty("keystrokes", String.valueOf(keystrokes));
        p.setProperty("cps", String.valueOf(cps)); p.setProperty("coords", String.valueOf(coords));
        p.setProperty("armor", String.valueOf(armor)); p.setProperty("ping", String.valueOf(ping));
        p.setProperty("potionHud", String.valueOf(potionHud)); p.setProperty("comboCounter", String.valueOf(comboCounter));
        p.setProperty("toggleSprint", String.valueOf(toggleSprint)); p.setProperty("noHurtCam", String.valueOf(noHurtCam));
        p.setProperty("fullbright", String.valueOf(fullbright)); p.setProperty("zoomEnabled", String.valueOf(zoomEnabled));
        p.setProperty("noFire", String.valueOf(noFire));
        p.setProperty("swordColor", String.valueOf(swordColor)); p.setProperty("swordRGB", String.valueOf(swordRGB));
        p.setProperty("pickaxeColor", String.valueOf(pickaxeColor)); p.setProperty("pickaxeRGB", String.valueOf(pickaxeRGB));
        p.setProperty("floatingSword", String.valueOf(floatingSword)); p.setProperty("shieldRed", String.valueOf(shieldRed));
        p.setProperty("critColor", String.valueOf(critColor)); p.setProperty("critRGB", String.valueOf(critRGB));
        p.setProperty("blockOutlineColor", String.valueOf(blockOutlineColor)); p.setProperty("outlineRGB", String.valueOf(outlineRGB));
        p.setProperty("keyMenu", String.valueOf(keyMenu)); p.setProperty("keyZoom", String.valueOf(keyZoom));
        p.setProperty("keyFullbright", String.valueOf(keyFullbright)); p.setProperty("keyNoHurtCam", String.valueOf(keyNoHurtCam));
        p.setProperty("keyToggleSprint", String.valueOf(keyToggleSprint)); p.setProperty("keyHud", String.valueOf(keyHud));
        p.setProperty("targetIndicator", String.valueOf(targetIndicator));
        p.setProperty("targetRGB", String.valueOf(targetRGB)); p.setProperty("targetDuration", String.valueOf(targetDuration));
        p.setProperty("comboTimeout", String.valueOf(comboTimeout));
        p.setProperty("viewModel", String.valueOf(viewModel));
        p.setProperty("vmPosX", String.valueOf(vmPosX)); p.setProperty("vmPosY", String.valueOf(vmPosY));
        p.setProperty("vmPosZ", String.valueOf(vmPosZ));
        p.setProperty("vmRotX", String.valueOf(vmRotX)); p.setProperty("vmRotY", String.valueOf(vmRotY));
        p.setProperty("vmRotZ", String.valueOf(vmRotZ)); p.setProperty("vmScale", String.valueOf(vmScale));
        p.setProperty("entityCulling", String.valueOf(entityCulling)); p.setProperty("cullDistance", String.valueOf(cullDistance));
        p.setProperty("noFog", String.valueOf(noFog)); p.setProperty("noWeather", String.valueOf(noWeather));
        p.setProperty("noClouds", String.valueOf(noClouds));
        p.setProperty("reduceParticles", String.valueOf(reduceParticles)); p.setProperty("particleLimit", String.valueOf(particleLimit));
        p.setProperty("skyTheme", String.valueOf(skyTheme));
        p.setProperty("customCrosshair", String.valueOf(customCrosshair));
        p.setProperty("crosshairRGB", String.valueOf(crosshairRGB));
        p.setProperty("crosshairSize", String.valueOf(crosshairSize));
        p.setProperty("crosshairThickness", String.valueOf(crosshairThickness));
        p.setProperty("hitSound", String.valueOf(hitSound));
        try (FileOutputStream out = new FileOutputStream(file())) { p.store(out, "Zorno Client v1.3"); } catch (IOException e) {}
    }

    public static void load() {
        File f = file(); if (!f.exists()) return;
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream(f)) { p.load(in); } catch (IOException e) { return; }

        hud=b(p,"hud",hud); keystrokes=b(p,"keystrokes",keystrokes);
        cps=b(p,"cps",cps); coords=b(p,"coords",coords);
        armor=b(p,"armor",armor); ping=b(p,"ping",ping);
        potionHud=b(p,"potionHud",potionHud); comboCounter=b(p,"comboCounter",comboCounter);
        toggleSprint=b(p,"toggleSprint",toggleSprint); noHurtCam=b(p,"noHurtCam",noHurtCam);
        fullbright=b(p,"fullbright",fullbright); zoomEnabled=b(p,"zoomEnabled",zoomEnabled);
        noFire=b(p,"noFire",noFire);
        swordColor=b(p,"swordColor",swordColor); swordRGB=i(p,"swordRGB",swordRGB);
        pickaxeColor=b(p,"pickaxeColor",pickaxeColor); pickaxeRGB=i(p,"pickaxeRGB",pickaxeRGB);
        floatingSword=b(p,"floatingSword",floatingSword); shieldRed=b(p,"shieldRed",shieldRed);
        critColor=b(p,"critColor",critColor); critRGB=i(p,"critRGB",critRGB);
        blockOutlineColor=b(p,"blockOutlineColor",blockOutlineColor); outlineRGB=i(p,"outlineRGB",outlineRGB);
        keyMenu=i(p,"keyMenu",keyMenu); keyZoom=i(p,"keyZoom",keyZoom);
        keyFullbright=i(p,"keyFullbright",keyFullbright); keyNoHurtCam=i(p,"keyNoHurtCam",keyNoHurtCam);
        keyToggleSprint=i(p,"keyToggleSprint",keyToggleSprint); keyHud=i(p,"keyHud",keyHud);
        targetIndicator=b(p,"targetIndicator",targetIndicator);
        targetRGB=i(p,"targetRGB",targetRGB); targetDuration=f(p,"targetDuration",targetDuration);
        comboTimeout=f(p,"comboTimeout",comboTimeout);
        viewModel=b(p,"viewModel",viewModel);
        vmPosX=f(p,"vmPosX",vmPosX); vmPosY=f(p,"vmPosY",vmPosY); vmPosZ=f(p,"vmPosZ",vmPosZ);
        vmRotX=f(p,"vmRotX",vmRotX); vmRotY=f(p,"vmRotY",vmRotY); vmRotZ=f(p,"vmRotZ",vmRotZ);
        vmScale=f(p,"vmScale",vmScale);
        entityCulling=b(p,"entityCulling",entityCulling); cullDistance=i(p,"cullDistance",cullDistance);
        noFog=b(p,"noFog",noFog); noWeather=b(p,"noWeather",noWeather);
        noClouds=b(p,"noClouds",noClouds);
        reduceParticles=b(p,"reduceParticles",reduceParticles); particleLimit=i(p,"particleLimit",particleLimit);
        skyTheme=i(p,"skyTheme",skyTheme);
        customCrosshair=b(p,"customCrosshair",customCrosshair);
        crosshairRGB=i(p,"crosshairRGB",crosshairRGB);
        crosshairSize=i(p,"crosshairSize",crosshairSize);
        crosshairThickness=i(p,"crosshairThickness",crosshairThickness);
        hitSound=b(p,"hitSound",hitSound);
    }
    private static boolean b(Properties p, String k, boolean d){ String v=p.getProperty(k); return v==null?d:Boolean.parseBoolean(v); }
    private static int i(Properties p, String k, int d){ try { return Integer.parseInt(p.getProperty(k, String.valueOf(d))); } catch (Exception e){ return d; } }
    private static float f(Properties p, String k, float d){ try { return Float.parseFloat(p.getProperty(k, String.valueOf(d))); } catch (Exception e){ return d; } }
}
