package com.lance.demo.microservice.loan.dao;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lance.demo.microservice.loan.entity.CreditLoanPO;
import com.lance.demo.microservice.loan.mapper.CreditLoanMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 信用支付-借款表  服务实现类
 * </p>
 *
 * @author author
 * @since 2017-12-01
 */
@Service
public class CreditLoanManager extends ServiceImpl<CreditLoanMapper, CreditLoanPO> implements IService<CreditLoanPO> {
	
}
