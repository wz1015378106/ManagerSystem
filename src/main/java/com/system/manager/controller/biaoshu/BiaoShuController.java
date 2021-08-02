package com.system.manager.controller.biaoshu;

import cn.hutool.core.util.StrUtil;
import com.system.manager.common.*;
import com.system.manager.service.biaoshu.BiaoShuService;
import com.system.manager.entity.biaoshu.BiaoShuEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: wangzhi
 * @Title: BiaoShuController
 * @Description:
 * @date: 2021/8/1 16:42
 */
@RestController
@RequestMapping("biaoshu")
@Api(tags = "标书")
public class BiaoShuController extends BaseController {

    @Autowired
    private BiaoShuService biaoShuService;

    @ApiOperation(value = "新增标书款")
    @PostMapping("add")
    public Result add(BiaoShuEntity biaoShuEntity){
        Date date = new Date();
        if (biaoShuEntity == null){
            throw new MyException(500,"参数为空！");
        }
        if (StrUtil.isBlank(biaoShuEntity.getOppositeUnit())){
            throw new MyException(500,"对方单位必填！");
        }
        if (StrUtil.isBlank(biaoShuEntity.getAccountNumber())){
            throw new MyException(500,"对方账号必填！");
        }
        if (StrUtil.isBlank(biaoShuEntity.getBankOfDeposit())){
            throw new MyException(500,"对方开户行必填！");
        }
        if (StrUtil.isBlank(biaoShuEntity.getUserId())){
            throw new MyException(500,"承担人必填！");
        }
        if (biaoShuEntity.getMoney() == null){
            throw new MyException(500,"金额必填！");
        }
        biaoShuEntity.setCreateTime(date);
        biaoShuEntity.setCreateBy(getUserName());
        biaoShuEntity.setUpdateTime(date);
        biaoShuEntity.setUpdateBy(getUserName());
        biaoShuService.add(biaoShuEntity);
        return Result.ok();
    }

    @GetMapping("queryPage")
    @ApiOperation(value = "分页查询标书单")
    public Result queryPage(@RequestParam(required = false) Map<String,Object> params){
        if (params == null){
            params = new LinkedHashMap<>();
        }
        params.put("userId",getUserId());
        PageUtils page = biaoShuService.queryPage(new Query(params));
        return Result.ok().put("page",page);
    }
    @GetMapping("queryPage")
    @ApiOperation(value = "删除标书款")
    public Result delete(String id){
        if (StrUtil.isEmpty(id)){
            throw new MyException("参数为空！");
        }
        biaoShuService.delete(id);
        return Result.ok();
    }
}
