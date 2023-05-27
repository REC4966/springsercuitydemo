package com.example.springsercuitydemo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springsercuitydemo.entity.MenuTable;

public interface menuMapper extends BaseMapper<MenuTable> {
    List<String> selectPermsByUserid(long userid);
}
