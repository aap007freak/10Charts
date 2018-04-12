package com.antonleagre.tencharts;

import java.io.File;
import java.time.LocalDateTime;

/**
 * this class gets entirely serialized to a loc using gson
 * It contains infos such as prefs, settings, update dates etc..
 */
public class Preferences {

    private File currentLocsFile;

    private File mostRecentFile;

}
