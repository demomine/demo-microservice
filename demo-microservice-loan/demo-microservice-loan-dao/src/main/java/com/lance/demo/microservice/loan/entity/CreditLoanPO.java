package com.lance.demo.microservice.loan.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 信用支付-借款表
 * </p>
 *
 * @author author
 * @since 2017-12-01
 */
@TableName("t_credit_loan")
@Data
public class CreditLoanPO implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.UUID)
	private String id;

	/**
	 * 借款类型 MANUAL人工，PAY_DAY_LOAN、AUTO_WITHDRAW为paymentcore自动体现类型，AUTO_WITHDRAW_ZFQ为paycenter自动体现类型
	 */
	@TableField(value="loan_type")
	private String loanType;

	/**
	 * 订单号
	 */
	@TableField(value="order_no")
	private String orderNo;

	/**
	 * 请求流水
	 */
	@TableField(value="req_no")
	private String reqNo;

	/**
	 * 机构号
	 */
	@TableField(value="org_code")
	private String orgCode;

	/**
	 * 机构名称
	 */
	@TableField(value="org_name")
	private String orgName;

	/**
	 * 产品号
	 */
	@TableField(value="product_code")
	private String productCode;

	/**
	 * 手机号
	 */
	@TableField(value="mobile_no")
	private String mobileNo;

	/**
	 * 客户账户号
	 */
	@TableField(value="contract_no")
	private String contractNo;

	/**
	 * 客户名称
	 */
	@TableField(value="contract_name")
	private String contractName;

	/**
	 * 货币
	 */
	private String currency;

	/**
	 * 申请金额
	 */
	@TableField(value="apply_amount")
	private BigDecimal applyAmount;

	/**
	 * 申请号(兼容后期上层传入)
	 */
	@TableField(value="apply_seq")
	private String applySeq;

	/**
	 * 
	 */
	@TableField(value="pay_amount")
	private BigDecimal payAmount;

	/**
	 * 实际贷款金额
	 */
	@TableField(value="loan_amount")
	private BigDecimal loanAmount;

	/**
	 * 银行名称
	 */
	@TableField(value="bank_name")
	private String bankName;

	/**
	 * 卡号后四位
	 */
	@TableField(value="card_suf")
	private String cardSuf;

	/**
	 * 分期期数
	 */
	private Integer stages;

	/**
	 * 返回流水号
	 */
	@TableField(value="pay_seq_no")
	private String paySeqNo;

	/**
	 * 渠道
	 */
	private String channel;

	/**
	 * 订单状态：初始审核中-APPROVE、审核成功-APPROVE_SUCCESS、审核失败-APPROVE_FAIL、提现成功-WITHDRAW_SUCCESS、提现失败-WITHDRAW_FAIL、提现处理中ACCEPT、取消借款CANCEL
	 */
	private String status;

	/**
	 * 返回信息
	 */
	@TableField(value="res_msg")
	private String resMsg;

	/**
	 * 失败原因
	 */
	@TableField(value="fail_reason")
	private String failReason;

	/**
	 * 设备id
	 */
	@TableField(value="device_id")
	private String deviceId;

	/**
	 * 服务IP
	 */
	@TableField(value="server_ip")
	private String serverIp;

	/**
	 * 审核时间
	 */
	@TableField(value="audit_date")
	private Date auditDate;

	/**
	 * 贷款时间
	 */
	@TableField(value="loan_date")
	private Date loanDate;

	/**
	 * 协议号
	 */
	@TableField(value="agreement_no")
	private String agreementNo;

	/**
	 * 借款目的
	 */
	private String purpose;

	/**
	 * 创建时间
	 */
	@TableField(value="created_at")
	private Date createdAt;

	/**
	 * 创建人
	 */
	@TableField(value="created_by")
	private String createdBy;

	/**
	 * 最后修改时间
	 */
	@TableField(value="updated_at")
	private Date updatedAt;

	/**
	 * 最后修改人
	 */
	@TableField(value="updated_by")
	private String updatedBy;

	/**
	 * 
	 */
	@TableField(value="accounting_no")
	private String accountingNo;

	/**
	 * 
	 */
	@TableField(value="accounting_date")
	private Date accountingDate;
}
