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
## 三、商品表
## 四、购物车表
## 五、订单表
## 六、订单明细表
## 七、支付表
## 八、地址表