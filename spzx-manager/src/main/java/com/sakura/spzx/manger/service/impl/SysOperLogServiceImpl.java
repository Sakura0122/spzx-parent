package com.sakura.spzx.manger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.spzx.model.entity.system.SysOperLog;
import com.sakura.spzx.manger.service.SysOperLogService;
import com.sakura.spzx.manger.mapper.SysOperLogMapper;
import org.springframework.stereotype.Service;

/**
* @author sakura
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service实现
* @createDate 2024-10-02 17:34:12
*/
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog>
    implements SysOperLogService{

}




