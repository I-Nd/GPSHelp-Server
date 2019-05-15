package com.example.study.service.impl;

import com.example.study.mapper.UnitMapper;
import com.example.study.object.Rescuer;
import com.example.study.object.Unit;
import com.example.study.mapper.RescuerMapper;
import com.example.study.service.RescuerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RescuerServiceImpl implements RescuerService {

    private final static Logger logger = LoggerFactory.getLogger(RescuerServiceImpl.class);

    @Autowired
    private RescuerMapper rescuerMapper;

    @Autowired
    private UnitMapper unitMapper;

    @Override
    public String getTaskId(String openId) {
        Rescuer rescuer = rescuerMapper.selectByOpenId(openId);
        return rescuer.getTaskId();
    }

    @Override
    public String[] getStatus(String openId) {
        Rescuer rescuer = rescuerMapper.selectByOpenId(openId);
        while(rescuer == null)  return null;
        return new String[]{rescuer.getId(), rescuer.getStatus()};
    }

    @Override
    public int register(String unitId, String unitKey, String name, String id, String gender, String phone, String openId) {
        Unit unit = unitMapper.selectByIdAndKey(unitId, unitKey);
        if(rescuerMapper.selectByPrimaryKey(unitId,id) != null){
            return 0;
        }else if(rescuerMapper.selectByOpenId(openId) != null){
            return 0;
        }else if (unit == null){
            return -1;
        }else{
            Rescuer rescuer = new Rescuer();
            rescuer.setUnitId(unit.getId());
            rescuer.setId(id);
            rescuer.setName(name);
            rescuer.setGender(gender);
            rescuer.setPhone(phone);
            rescuer.setOpenId(openId);
            rescuer.setStatus("在线");
            return rescuerMapper.insert(rescuer);
        }
    }

    @Override
    public int changeStatus(String openId, String status) {
        Rescuer rescuer = rescuerMapper.selectByOpenId(openId);
        rescuer.setStatus(status);
        return rescuerMapper.updateByPrimaryKey(rescuer);
    }

}
