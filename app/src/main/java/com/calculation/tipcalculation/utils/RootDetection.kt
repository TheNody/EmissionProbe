package com.calculation.tipcalculation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object RootDetection {

    private val knownRootAppsPackages = arrayOf(
        "com.noshufou.android.su", "com.thirdparty.superuser", "eu.chainfire.supersu",
        "com.koushikdutta.superuser", "com.zachspong.temprootremovejb", "com.ramdroid.appquarantine",
        "com.topjohnwu.magisk", "com.kingroot.kinguser", "com.koushikdutta.rommanager",
        "com.dimonvideo.luckypatcher", "com.chelpus.luckypatcher", "com.devadvance.rootcloak",
        "com.saurik.substrate", "com.amphoras.hidemyroot", "com.formyhm.hideroot",
        "com.formyhm.hiderootPremium", "de.robv.android.xposed.installer", "org.meowcat.edxposed.manager",
        "com.devadvance.rootcloak2", "com.jrummy.busybox.installer", "stericson.busybox"
    )

    private val knownDangerousAppsPackages = arrayOf(
        "com.koushikdutta.rommanager", "com.dimonvideo.luckypatcher", "com.chelpus.luckypatcher",
        "com.topjohnwu.magisk", "com.jrummy.busybox.installer", "stericson.busybox",
        "com.zachspong.temprootremovejb", "com.amphoras.hidemyroot", "com.formyhm.hideroot",
        "com.formyhm.hiderootPremium", "de.robv.android.xposed.installer", "org.meowcat.edxposed.manager",
        "com.noshufou.android.su", "com.thirdparty.superuser", "eu.chainfire.supersu",
        "com.koushikdutta.superuser", "com.ramdroid.appquarantine", "com.saurik.substrate"
    )

    private val paths = arrayOf(
        "/system/app/Superuser.apk", "/system/xbin/su", "/system/bin/su",
        "/system/bin/.ext/.su", "/system/bin/failsafe/su", "/data/local/su",
        "/data/local/bin/su", "/data/local/xbin/su", "/sbin/su", "/su/bin/su",
        "/magisk/.core/bin/su", "/su/xbin/su", "/su/bin/su", "/data/local/tmp/su",
        "/data/local/xbin/su", "/system/sd/xbin/su", "/system/etc/init.d/99SuperSUDaemon",
        "/system/xbin/daemonsu", "/system/sbin/su", "/system/bin/.ext/.su",
        "/system/usr/we-need-root/su-backup", "/system/xbin/mu", "/system/xbin/sugote",
        "/system/xbin/sugote_mksh", "/system/bin/failsafe/su"
    )

    fun isDeviceRooted(context: Context): Boolean {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4(context)
    }

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        return paths.any { File(it).exists() }
    }

    private fun checkRootMethod3(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            bufferedReader.readLine() != null
        } catch (e: Exception) {
            false
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun checkRootMethod4(context: Context): Boolean {
        val packageManager = context.packageManager
        return try {
            val installedPackages = packageManager.getInstalledPackages(0)
            installedPackages.any { packageInfo ->
                knownRootAppsPackages.contains(packageInfo.packageName) || knownDangerousAppsPackages.contains(packageInfo.packageName)
            }
        } catch (e: Exception) {
            false
        }
    }
}