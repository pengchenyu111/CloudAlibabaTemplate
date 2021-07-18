# CloudAlibabaTemplate

项目GitHub地址：https://github.com/pengchenyu111/CloudAlibabaTemplate

# 一、简介

​	本项目使用Spring Cloud Alibaba的技术组件来进行开发，涉及到了服务拆分、服务限流降级熔断、鉴权、远程调用、短信邮件服务等，编写此项目的目的是为了以此为模板，以后编写新项目时可以直接把其中的一些模块拿来用。

​	本人之前为了拓宽技术栈，在B站上学习Spring Cloud Alibaba，老师讲的不错，这里推荐一下链接：https://www.bilibili.com/video/BV1nK4y1j7gL 

但是学习后发现没有项目实战一切都是瞎掰，因此自己写了一个小demo。此外推荐一本书：***Spring Cloud Alibaba 微服务原理与实战*** 其中的原理讲的很清楚了。

# 二、技术栈

- 数据库：MySQL
- 持久层：Mybatis-plus
- Spring Cloud Alibaba相关：
  - 服务注册与发现：Nacos
  - 服务限流、降级、熔断：Sentinel
  - 分布式事务：Seata
  - 消息中心：Spring Coud Stream
    - 消息中间件：RocketMQ
  - 网关：Gateway
  - 鉴权：Spring Cloud OAuth2
  - 远程调用：Open Feign
- 缓存：Redis、JetCache
- 短信邮件服务：Aliyun
- 接口文档：Swagger
- 其他中间件：
  - Java bean工具：Lombok
  - 对象映射工具：Mapstruct
  - 常用工具包：Hutool
  - JSON序列化工具：Jackson

# 三、环境及基础软件安装

​	本人由于没钱，只能在VMWare上搞了台虚拟机。

​	硬件配置：2核4G内存，40G硬盘

​	软件配置：OS：Centos7

​	至于上述技术栈中要安装的中间件，本人选择用docker来简化安装配置过程，其详细的安装过程请参考项目文件夹下的**基础软件的安装.docx**，若还需要安装其他软件。请参考：https://blog.csdn.net/qq_43284141/article/details/111249765

​	某些子模块中的application.yml配置文件中的配置是去nacos中拉取的，这些配置在**nacos_config.sql**中，所以你需要配置nacos的持久化。

# 四、项目模块说明

详细介绍请参考我的CSDN博客：https://blog.csdn.net/qq_43284141

| 模块名                        | 功能                 | 说明                                   |
| :---------------------------- | -------------------- | -------------------------------------- |
| template-common               | 项目的公共模块       |                                        |
| template-authorization-server | 鉴权中心             | 有些接口必须使用Token才有权访问        |
| template-gateway-server       | 网关                 | 接口统一 入口                          |
| template-movie-server         | 业务微服务：电影     | 业务微服务都是根据项目的具体业务编写的 |
| template-general-user-server  | 业务微服务：普通用户 | 业务微服务都是根据项目的具体业务编写的 |
| template-sms-mail-server      | 短信邮件服务         | 基于阿里云提供的接口开发               |

注：某些模块中分为api和service子模块。api中为Java Bean和feign远程调用接口，service中为具体的业务逻辑。

## 4.1 template-common

此模块中存放整个项目的常用配置、工具和常量等，以后每个子模块都应依赖该模块。

### 4.1.1 切面

- WebLogAspect

  接口调用日志切面，详细记录的接口的调用url、请求类型、请求参数、返回结果、消耗时间等。

- GlobalExceptionHandler

  全局异常处理器

### 4.1.2 配置

- ResourceServerConfig

  资源服务器配置，在这里读取公钥，并配置JWT转换器、Token仓库和需要权限认证的路径等。

  有关公钥、私钥的生成与使用请参考**template-authorization-server子模块中的密钥说明.txt**文件。

- SwaggerAutoConfiguration

  Swagger接口文档的配置，注意配置了安全规则，那么在网页上浏览接口说明时，只有正确的token才能使用接口发送和接收数据。

- RedisConfig

  Redis的相关配置

- MybatisPlusConfig

  Mybatis-plus的相关配置，分页插件、乐观锁和ID生成器

- OAuth2FeignConfig

  feign远程调用鉴权相关配置，主要看为了传递token

- 还有几个配置，由于比较简单，这里不一一展开了

### 4.1.3 model和常量

本项目所有接口的返回值都用**ResponseObject<T>**包装；

常量中存放了如错误码等其他功能常量

## 4.2 template-authorization-server

### 4.2.1 配置

- AuthorizationServerConfig和WebSecurityConfig

  这是鉴权中心最重要的两个配置类，这两个类配置了需要鉴权的路径和Token的相关配置。Token分为外部和内部Token，这是因为项目中供外部调用者使用的接口需要外部token（token中含有用户信息）来鉴权，而在当服务与服务之间相互调用时，不需要用户信息所以需要另一种token。

  ```java
  @Override
      public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
          clients.inMemory()
                  // 第三方客户端的名称
                  .withClient(OUTSIDE_AUTH_NAME)
                  // 第三方客户端的密钥
                  .secret(passwordEncoder.encode(OUTSIDE_AUTH_SECRET))
                  // 第三方客户端的授权范围
                  .scopes("all")
                  .authorizedGrantTypes("password", "refresh_token")
                  // token的有效期
                  .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY)
                  // refresh_token的有效期
                  .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY)
                  .and()
                  .withClient(INSIDE_AUTH_NAME)
                  .secret(passwordEncoder.encode(INSIDE_AUTH_SECRET))
                  .authorizedGrantTypes("client_credentials")
                  .scopes("all")
                  .accessTokenValiditySeconds(INSIDE_TOKEN_VALIDITY);
          super.configure(clients);
      }
  ```


### 4.2.2 登录鉴权实现

​	UserServiceDetailsServiceImpl 实现 UserDetailsService接口，在登录时获取用户的账号、密码、身份和权限等信息，用来和用户的输入进行比较。

​	注意：此处不是真正的登录，只是鉴权，具体登录要在比如普通用户或管理员的模块中去实现。

```java
 @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 通过login_type区分是管理员还是普通用户登录
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        UserDetails userDetails = null;
        try {
            // 若通过refresh_token获取新token，则对username进行纠正
            String grantType = requestAttributes.getRequest().getParameter("grant_type");
            if (LoginConstant.REFRESH_TYPE.equals(grantType.toUpperCase())) {
                username = adjustUsername(username, loginType);
            }
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    userDetails = loadSysUserByUsername(username);
                    break;
                case LoginConstant.GENERAL_USER_TYPE:
                    userDetails = loadGeneralUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式:" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }
        return userDetails;
    }
```

### 4.2.3 获取token

- 获取外部token

  访问：http://127.0.0.1:9999/oauth/token?grant_type=password&username=18224464804&password=123456&login_type=generAuthal_user_type

  注意同时在Authorization中设置Basic Auth，填写你设置的**外部**访问的username和password

- 获取内部token

  访问：http://127.0.0.1:9999/oauth/token?grant_type=client_credentials

  注意同时在Authorization中设置Basic Auth，填写你设置的**内部**访问的username和password

## 4.3 template-gateway-server

​	网关是这个项目的一个重要组成部分，我们将在这个部分来做接口的访问限制，包括访问路径、限流、降级、熔断等。

### 4.3.1 配置

在配置文件中编写了某些子模块的访问路径，以及Sentinel结合Nacos的限流降级规则：

```yaml
server:
  port: 80
spring:
  application:
    name: gateway-server
  cloud:
    nacos:
      server-addr: 192.168.126.13:8848
      discovery:
        namespace: 0d70f7cc-3925-4f2a-a212-bd7053c89864
        group: DEFAULT_GROUP
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: movie-server-service_router
          uri: lb://movie-server-service
          predicates:
            - Path=/movie_detail/**
        - id: general-user-server-service_router
          uri: lb://general-user-server-service
          predicates:
            - Path=/user/**
        - id: sms-mail-server-service_router
          uri: lb://sms-mail-server-service
          predicates:
            - Path=/sms_mail/**

    # Sentinel 限流与降级
    sentinel:
      transport:
        dashboard: 192.168.126.13:8858
      datasource:
        # 用Nacos做规则持久化
        ds1-flow.nacos:
          serverAddr: 192.168.126.13:8848
          namespace: 0d70f7cc-3925-4f2a-a212-bd7053c89864
          groupId: DEFAULT_GROUP
          dataId: gw-flow.json
          ruleType: gw-flow
        ds2-api-group.nacos:
          serverAddr: 192.168.126.13:8848
          namespace: 0d70f7cc-3925-4f2a-a212-bd7053c89864
          groupId: DEFAULT_GROUP
          dataId: api-group.json
          ruleType: gw-api-group
        ds3-degrade.nacos:
          serverAddr: 192.168.126.13:8848
          namespace: 0d70f7cc-3925-4f2a-a212-bd7053c89864
          groupId: DEFAULT_GROUP
          dataId: gw-degrade.json
          ruleType: degrade
  redis:
    host: 192.168.126.13
    port: 6379
    password: Pcy90321.
```

Nacos中持久化的限流降级规则如下，具体每个字段的含义可以通过查看**AbstractRule的实现类**来了解：

gw-flow.json

```json
[
  {
    "resource": "movie-server-service_router",
    "resourceMode": 0 ,
    "grade": 1,
    "count": 5,
    "intervalSec": 2,
    "controlBehavior": 0,
    "burst": 2,
    "maxQueueingTimeoutMs": 500
  },
  {
    "resource": "login-api-group",
    "resourceMode": 1,
    "grade": 1,
    "count": 2,
    "intervalSec": 2,
    "controlBehavior": 0,
    "burst": 2,
    "maxQueueingTimeoutMs": 500
  }
]
```

api-group.json

```json
[
  {
    "apiName": "login-api-group",
    "predicateItems": [
      {
        "pattern": "/user/login"
      },
      {
        "pattern": "/admin/login"
      }
    ]
  }
]
```

gw-degrade.json

```json
[
    {
        "resource": "movie-server-service_router",
        "limitApp": "default",
        "grade": 0,
        "count": 500,
        "timeWindow": 2,
        "minRequestAmount": 5,
        "slowRatioThreshold": 1.0,
        "statIntervalMs": 1000
    }
]
```

### 4.3.2 过滤器

- JwtCheckFilter

  过滤出所有从网关走的且需要token访问的接口，访问时该接口携带的token必须有效才能通过网关。



# 4.4 template-sms-mail-server

该模块为短信邮件服务，此模块包括短信邮件的发送服务和发送记录详情服务。此模块中，只有发送详情服务提供feign远程调用服务，发送服务是通过**消息**来异步调用的。具体的发送示意如下：

调用者发送消息===>Spring cloud Stream ===>消费者接收消息===>执行本地事务（发邮件、存记录）

**注意：在这里的消息都是事务消息！**

### 4.4.1 事务消息的消费

```java
@Slf4j
@RocketMQTransactionListener(txProducerGroup = MQConstant.MAIL_TX_GROUP)
public class MailTransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailSendRecordService mailSendRecordService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String msg = new String((byte[]) message.getPayload());
            ObjectMapper objectMapper = new ObjectMapper();
            MailMessage mailMessage = objectMapper.readValue(msg, MailMessage.class);
            String txId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
            // 发送短信
            boolean flag = mailService.singleSendMailTo(mailMessage, txId);
            return flag ? RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.ROLLBACK;
        } catch (JsonProcessingException e) {
            log.info("Json转换出错，msg => {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        } catch (ClientException e) {
            log.info("邮件接口调用出错，requestId => {}，errCode => {}，errMsg => {}，errorDescription => {}",
                    e.getRequestId(), e.getErrCode(), e.getErrMsg(), e.getErrorDescription());
            return RocketMQLocalTransactionState.ROLLBACK;
        } catch (Exception e) {
            log.info("msg => {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String txId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        log.info("检查事务id => {}", txId);
        MailSendRecord record = mailSendRecordService.queryMailSendRecordByTxId(txId);
        return record == null ? RocketMQLocalTransactionState.ROLLBACK : RocketMQLocalTransactionState.COMMIT;
    }
}
```

**其中singleSendMailTo方法上使用Seata的@GlobalTransactional注解来开启事务**

```java
    @GlobalTransactional
    public boolean singleSendMailTo(MailMessage mailMessage, String txId) throws Exception {
        // 发送邮件
        MailSendRecord record = singleSendMail(mailMessage, txId);
        // 存入发送记录到数据库
        boolean isStore = storeMailRecord(record);
        return record != null && isStore;
    }
```

### 4.4.2  阿里云的短信和邮件发送接口

***其中的ACCESS_KEY请使用你自己的阿里云的，否则本人在阿里云控制台上一旦发现您的盗取使用，我将追究您的赔偿责任！***

***其中的ACCESS_KEY请使用你自己的阿里云的，否则本人在阿里云控制台上一旦发现您的盗取使用，我将追究您的赔偿责任！***

***其中的ACCESS_KEY请使用你自己的阿里云的，否则本人在阿里云控制台上一旦发现您的盗取使用，我将追究您的赔偿责任！***



邮件发送接口：

```java
private MailSendRecord singleSendMail(MailMessage mailMessage, String txId) throws Exception {
        IAcsClient client = createClient(MailConstant.REGION, MailConstant.ACCESS_KEY_ID, MailConstant.ACCESS_KEY_SECRET);
        SingleSendMailRequest request = new SingleSendMailRequest();
        // 发信地址
        request.setAccountName(mailMessage.getAccountName());
        // 0：为随机账号 1：为发信地址
        request.setAddressType(1);
        // 邮件标签，和阿里云上保持一致
        request.setTagName(mailMessage.getTagName());
        // 是否启用管理控制台中配置好回信地址（状态须验证通过），取值范围是字符串true或者false
        request.setReplyToAddress(true);
        // 目标地址
        request.setToAddress(mailMessage.getToAddress());
        // 邮件主题
        request.setSubject(mailMessage.getSubject());
        //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
        //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
        request.setHtmlBody(mailMessage.getMailHTMLBody());
        request.setTextBody(mailMessage.getMailTextBody());
        // 调用阿里云接口发送邮件
        SingleSendMailResponse singleSendMailResponse = client.getAcsResponse(request);
        log.info("发件人 => {}，收件人 => {}，请求id => {}", mailMessage.getAccountName(), mailMessage.getToAddress(), singleSendMailResponse.getRequestId());
        // 装配返回对象
        MailSendRecord record = MailSendRecord.builder()
                .accountName(mailMessage.getAccountName())
                .toAddress(mailMessage.getToAddress())
                .subject(mailMessage.getSubject())
                .tagName(mailMessage.getTagName())
                .mailHtmlBody(mailMessage.getMailHTMLBody())
                .mailTextBody(mailMessage.getMailTextBody())
                .sendTime(DateUtil.parse(DateUtil.now()))
                .successFlag(singleSendMailResponse.getEnvId() == null ? "0" : "1")
                .requestId(singleSendMailResponse.getRequestId())
                .transactionId(txId)
                .build();
        return record;
    }
```

短信发送接口：

```java
@GlobalTransactional
    public boolean sendVerificationTo(String phoneNumber, String txId) throws Exception {
        // 发送验证码短信
        Client client = createClient(SmsConstant.ACCESS_KEY_ID, SmsConstant.ACCESS_KEY_SECRET);
        String verificationCode = generateVerifyCode();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName(SmsConstant.SIGN_NAME)
                .setTemplateCode(SmsConstant.TEMPLATE_CODE)
                .setTemplateParam("{code:" + verificationCode + "}");
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        boolean isSuccess = "OK".equals(sendSmsResponse.getBody().getCode());
        log.info("目标用户 => {}，验证码 => {}，信息发送是否发送成功 => {}", phoneNumber, verificationCode, isSuccess);
        // 数据库存入记录
        VerificationCodeSendRecord record = VerificationCodeSendRecord.builder()
                .phoneNumber(phoneNumber)
                .verificationCode(verificationCode)
                .sendTime(DateUtil.parse(DateUtil.now()))
                .successFlag(isSuccess ? "1" : "0")
                .requestId(sendSmsResponse.getBody().getRequestId())
                .transactionId(txId)
                .build();
        boolean isStored = verificationCodeSendRecordService.save(record);
        return isSuccess && isStored;
    }
```

# 结语

开源万岁，拥抱开源！

如果您觉得我的项目写的不错，请给我的github项目一颗小星星哦！