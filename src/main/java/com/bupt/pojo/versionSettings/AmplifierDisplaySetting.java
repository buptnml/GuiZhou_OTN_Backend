package com.bupt.pojo.versionSettings;

import java.io.Serializable;

public class AmplifierDisplaySetting  implements Serializable {
    private boolean hasAmplifierId;
    private boolean hasAmplifierName;
    private boolean hasGain;
    private boolean hasMinimumInputPower;
    private boolean hasMaximumInputPower;
    private boolean hasMaximumOutputPower;

    private String amplifierIdRename;
    private String amplifierNameRename;
    private String gainRename;
    private String minimumInputPowerRename;
    private String maximumInputPowerRename;
    private String maximumOutputPowerRename;
}
