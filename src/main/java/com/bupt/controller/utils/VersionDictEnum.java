package com.bupt.controller.utils;

/*定义了每个版本中可能存在的数据操作枚举*/
public enum VersionDictEnum {
    /*linkType必须要在link前面！！！！！*/
    /*由于顺序需求，bussiness在前面，amplifier在最后，收尾顺序不允许更改*/
    bussiness, disk, netElement, linkType, link, amplifier
}
