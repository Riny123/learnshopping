# 2018-12-03 项目需求分析
## 电商项目需求分析
### 核心--购买
#### 1、用户模块
##### 登录
##### 注册
##### 忘记密码
##### 获取用户信息
##### 修改密码
##### 退出登录
#### 2、商品模块
##### 后台
###### 添加商品
###### 修改商品
###### 删除商品
###### 商品的上下架
###### 查看商品
##### 前台（门户）
###### 搜索商品
###### 查看商品详情
#### 3、类别模块
##### 添加类别
##### 修改类别名称
##### 删除类别
##### 查看类别
##### 查看子类
##### 查看后代类别
#### 4、购物车模块
##### 添加到购物车
##### 修改购物车中某个商品的数量
##### 删除购物车的商品（可以删除一个也可以多个）
##### 全选/取消全选
##### 单选/取消单选
##### 查看购物车中商品数量
#### 5、地址模块
##### 添加地址
##### 修改地址
##### 删除地址
##### 查看地址
#### 6、订单模块
##### 前台（门户）
###### 下订单
###### 查看订单列表
###### 取消订单
###### 查看订单详情
##### 后台
###### 查看订单列表
###### 查看订单详情
###### 发货
#### 7、支付模块
##### 支付宝支付
##### 支付（接口）
##### 支付回调
##### 查看支付状态
#### 8、线上部署
##### 阿里云部署



# 2018-12-03 Git笔记
## git的一些命令
#### Git是一款免费并且开源的分布式版本控制系统
#### 基础配置
##### 配置用户名
###### git config --global user.name "用户名"
##### 配置邮箱
###### git config --global user.email "邮箱"
##### 配置编码形式
###### 避免git gui中的中文乱码   git config --global gui.encoding utf-8
###### 避免git status显示的中文文件名乱码   git config --global core.quotepath off
##### 配置其他
###### git config --global core.ignorepath false
##### git ssh key pair配置
###### ssh-keygen -t rsa -C "邮箱名"
##### 创建本地仓库
###### git init
##### 添加到暂存区
###### git add 文件名
##### 提交到本地仓库
###### git commit -m "描述"
##### 检查工作区文件状态
###### git status
##### 查看已提交的状态
###### git log
##### 版本回退
###### git reset --hard 版本的前几位数字
##### 查看分支
###### git branch
##### 创建和切换到某某分支
###### git checkout -b 分支名
##### 切换分支
###### git checkout 分支名
##### 拉取
###### git pull
##### 第一次提交到远程仓库
###### git push -u origin master
##### 以后再提交到远程仓库
###### git push origin master 或者 git push
##### 分支合并
###### git merge 分支名
##### 把本地分支推送到远程分支
###### git push origin HEAD -u
##### 提交所有到暂存区
###### git add .
##### 连接远程仓库
###### git remote add origin 地址
# ----------2018-12-04-----------
##### 远程分支的合并
###### git checkout dev
###### git pull origin dev
###### git checkout master
###### git merge dev
###### git push origin master

# ---------------电商项目的数据库设计----------------
## 创建数据库
```
create database ilearnshopping;
use ilearnshopping;
```
## 一、用户表
```
create table neuedu_user(
`id` int(11) not null auto_increment comment '用户id',
`username` varchar(50) not null comment '用户名',
`password` varchar(50) not null comment '密码',
`email` varchar(50) not null comment '邮箱',
`phone` varchar(11) not null comment '联系方式',
`question` varchar(100) not null comment '密保问题',
`answer` varchar(100) not null comment '答案',
`role` int(4) not null default 0 comment '用户角色',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`),
unique key `user_name_index`(`username`) using btree
)ENGINE=INNODB default charset=utf8;
```
## 二、类别表
```
create table neuedu_category(
`id` int(11) not null auto_increment comment '类别id',
`parent_id` int(11) not null default 0 comment '父类id',
`name` varchar(50) not null comment '类别名称',
`status` int(4) default 1 comment '类别状态 1:正常 0:废弃',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`)
)ENGINE=INNODB default charset=utf8;
```
## 三、商品表
```
create table neuedu_product(
`id` int(11) not null auto_increment comment '商品id',
`category_id` int(11) not null comment '商品所属类别id，值引用类别表id',
`name` varchar(100) not null comment '商品名称',
`detail` text comment '商品详情',
`subtitle` varchar(200) comment '商品副标题',
`main_image` varchar(100) comment '商品主图',
`sub_image` varchar(100) comment '商品子图',
`price` decimal(20,2) not null comment '商品价格，总共20位，小数两位，整数18位',
`stock` int(11) comment '商品库存',
`status` int(4) default 1 comment '商品状态 1:在售 2:下架 3:删除',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`)
)ENGINE=INNODB default charset=utf8;
```
## 四、购物车表
```
create table neuedu_cart(
`id` int(11) not null auto_increment comment '购物车id',
`user_id` int(11) not null comment '用户id',
`product_id` int(11) not null comment '商品id',
`quantity` int(11) not null comment '购买数量',
`checked` int(4) default 1 comment '1:选中 0:未选中',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`),
key `user_id_index`(`user_id`) using btree
)ENGINE=INNODB default charset=utf8;
```
## 五、订单表
```
create table neuedu_order(
`id` int(11) not null auto_increment comment '订单id',
`order_no` bigint(20) not null comment '订单号',
`user_id` int(11) not null comment '用户id',
`payment` decimal(20,2) not null comment '付款总金额，单位元，保留两位小数',
`payment_type` int(4) not null default 1 comment '支付方式 1:线上支付',
`status` int(10) not null comment '订单状态 0:已取消 10:未付款 20:已付款 30:已发货 40:已完成 50:已关闭',
`shiping_id` int(11) not null comment '收货地址id',
`postage` int(10) not null default 0 comment '运费',
`payment_time` datetime default null comment '已付款时间',
`send_time` datetime default null comment '已发货时间',
`close_time` datetime default null comment '已关闭时间',
`finish_time` datetime default null comment '已完成时间',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`),
unique key `order_no_index`(`order_no`) using btree
)ENGINE=INNODB default charset=utf8;
```
## 六、订单明细表
```
create table neuedu_order_item(
`id` int(11) not null auto_increment comment '订单明细id',
`order_no` bigint(20) not null comment '订单号',
`user_id` int(11) not null comment '用户id',
`product_id` int(11) not null comment '商品id',
`product_name` varchar(100) not null comment '商品名称',
`product_image` varchar(100) comment '商品主图',
`current_unit_price` decimal(20,2) not null comment '下单时商品的价格，元为单位，保留两位小数',
`quantity` int(10) not null comment '商品的购买数量',
`total_price` decimal(20,2) not null comment '商品的总价格，元为单位，保留两位小数',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`),
key `order_no_index`(`order_no`) using btree,
key `order_no_user_id_index`(`order_no`,`user_id`) using btree
)ENGINE=INNODB default charset=utf8;
```
## 七、支付表
```
create table neuedu_payinfo(
`id` int(11) not null auto_increment comment '支付信息id',
`user_id` int(11) not null comment '用户id',
`order_no` bigint(20) not null comment '订单号',
`pay_platform` int(4) not null default 1 comment '支付方式 1:支付宝 2:微信',
`platform_status` varchar(50) comment '支付状态',
`platform_number` varchar(100) comment '流水号',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`)
)ENGINE=INNODB default charset=utf8;
```
## 八、地址表
```
create table neuedu_shipping(
`id` int(11) not null auto_increment comment '地址id',
`user_id` int(11) not null comment '用户id',
`receiver_name` varchar(50) default null comment '收货人姓名',
`receiver_phone` varchar(11) default null comment '收货人固定电话',
`receiver_mobile` varchar(11) default null comment '收货人移动电话',
`receiver_province` varchar(20) default null comment '省份',
`receiver_city` varchar(20) default null comment '城市',
`receiver_district` varchar(20) default null comment '区/县',
`receiver_address` varchar(200) default null comment '详细地址',
`receiver_zip` varchar(6) default null comment '邮编',
`create_time` datetime comment '创建时间',
`update_time` datetime comment '修改时间',
primary key(`id`)
)ENGINE=INNODB default charset=utf8;
```

# ---------------项目架构--基于四层架构-----------------
```
一、视图层（能看见页面，表单）
二、控制层（Controller层并且调用业务逻辑层）
三、业务逻辑层（Service层:处理业务逻辑并且调用DAO层）
        接口  和  实现类
四、DAO层（跟数据库的交互:增删改查，dao层负责对数据库的增删改查）
```

# ----------------Mybastis-generator插件---------------

# ----------------搭建ssm框架---------------
# ----------------Controller层---------------
```
/*@Controller直接跳转到前端页面*/
/*@RestController往前端返回的是一个json的数据*/
@RestController
@RequestMapping(value = "/portal/user/")
public class TestController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login.do")
    /*@RequestParam的value值必须与要传入的值相对应*/
    public int login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "phone") String phone,
                          @RequestParam(value = "question")String question,
                          @RequestParam(value = "answer")String answer){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setQuestion(question);
        userInfo.setAnswer(answer);
        userInfo.setRole(1);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        int count = iUserService.register(userInfo);
        return count;
    }
```
```
    @RequestMapping(value = "login.do")
    /**@RequestParam里面的参数：
     *                            value：里面的值必须与闯入的参数值相同，
     *                                   如果value值与后面定义的形参名字相同，可以不加该注释
     *                            required：默认值为true，代表参数是必须传递的，不传就报错，可以设置为false，可传可不传
     *                            defaultValue：假如有value值，后面设置为false，给它一个默认值
    */
    public int login(String username, String password, String email, String phone, String question, String answer){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setQuestion(question);
        userInfo.setAnswer(answer);
        userInfo.setRole(1);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        int count = iUserService.register(userInfo);
        return count;
    }

```
```
@RequestMapping(value = "login.do")
    /**@RequestParam里面的参数：
     *                            value：里面的值必须与闯入的参数值相同，
     *                                   如果value值与后面定义的形参名字相同，可以不加该注释
     *                                   也可以在省略，直接对象绑定，用对象接收
     *                            required：默认值为true，代表参数是必须传递的，不传就报错，可以设置为false，可传可不传
     *                            defaultValue：假如有value值，后面设置为false，给它一个默认值
    */
    public int login(UserInfo userInfo){
        int count = iUserService.register(userInfo);
        return count;
    }
```
# ----------------------2018-12-05：项目登录接口--------------------------
## ------------------登录------------------
### 先根据接口文档编写controller层，然后对应其业务逻辑层
### service层的接口实现按照步骤来完成
```
//step1:进行参数的非空校验
//step2:检查username是否存在
//step3:根据用户名和密码查询
//step4:处理结果并返回
```
### 进行参数的非空校验先用简单的方式
```
if (username == null || username.equals("")){
   return ServerReponse.createServerResponseByError("用户名不能为空");
}
if (password == null || password.equals("")){
    return ServerReponse.createServerResponseByError("密码不能为空");
}
```
### 进行参数校验的时候apache组件提供了一些校验的组件
```
<!--提供了一些常用的校验方法-->
    <dependency>
      <groupId>commons-lang</groupId>
      <artifactId>commons-lang</artifactId>
      <version>2.6</version>
    </dependency>
    
StringUtils.isBlank()判断字符串是不是空的，有一种特殊形式是" "直接判断字符串是空
StringUtils.isEmpty()判断字符串是不是空的，它不能判断" "是空的
```
### 检查username是否存在
#### 直接在dao接口中声明方法，然后在dao接口的mapper里面实现sql语句；
#### mapper里面sql语句中有属性
```
1：id：与dao接口中的方法名相同
2：parameterType参数类型：如果dao接口中参数只有一个，其参数类型就为该参数类型
                          如果dao接口中参数为多个，其参数类型为map
3：resultType返回结果类型：如果dao接口中参数只有一个，返回结果类型就为dao接口中方法的返回值类型
3：resultMap返回结果类型：如果dao接口中参数为多个，返回结果类型就为BaseResultMap
```
#### 如果在dao接口中声明方法时的参数为一个时，直接在sql语句中#{参数}，
#### 如果在dao接口中声明方法时的参数为多个时，需要在每个参数前面加上@Param()注解，然后在映射文件中直接写对应的key值，也就是注解里面的值