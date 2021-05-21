# GitHub 学习日志

***//关于分支和分支指针？***

***//这里语法细节不懂***

***4个脚标待补充***

## GitHub 简明指南

**搭建、连接完成后的基本的工作流程：**

1. 写代码
2. `git add .`添加所有到缓存区域
3. `git commit -m "message"`提交到版本库并备注了注释信息
4. `git remote -v` `git push origin Abranch`查看远程仓库名以及

### 工作流-add-commit-push



你的**本地仓库**由 git 维护的三棵“树”组成。

你的**本地仓库**由 git 维护的三棵“树”组成。

你的**本地仓库**由 git 维护的三棵“树”组成。



* 第一个是你的 `工作目录`，它持有实际文件；
* 第二个是 `暂存区（Index）`，它像个缓存区域，临时保存你的改动； 
* 最后是 `HEAD`，它指向你最后一次提交的结果。[^分支和分支指针] 

> HEAD 是一个指针，HEAD指针通常会指向一个分支（或者说指向一个分支指针）





![img](http://rogerdudler.github.io/git-guide/img/trees.png)



#### 添加 (add) 和提交 (commit)



* 添加/更改（把它们添加到暂存区），使用如下命令：

  `git add <filename>` `git add *`

  这是 git 基本工作流程的第一步；

* 提交，使用如下命令以**实际**提交改动：

  `git commit -m "代码提交信息"` [^commit语法]

  现在改动已经提交到了 **HEAD**，但是还没到你的**远端**仓库。



#### 推送改动 (push)



你的改动现在已经在本地仓库的 HEAD 中了。执行如下命令以将这些改动提交到远端仓库（推送）：

`git push origin master`
可以把 *master* 换成你想要推送的任何分支。

如果你*还没有克隆* 现有仓库，并欲将你的仓库**连接到某个远程服务器**，你可以使用如下命令添加：
`git remote add origin <server>`
如此你就能够将你的改动推送到所添加的服务器上去了。

> ##### 为什么需要克隆？
> git的**版本库目录**和**工作区**是在一起的，因此如果删除一个项目的工作区，同时也会把这个项目的版本库删除掉。这样一来，项目也就不复存在了，因此一个项目仅在一个工作区中维护太危险了，所以此时可以使用对项目创建备份库
> 通常情况下我们创建两个相同的项目，可以**使用git pull和git push命令来实现两个项目间的同步**。而git又提供了一个克隆的命令：git clone来简化项目间的同步。



### 时光机



`git status`时刻掌握仓库当前状态

`git diff`展示修改内容 (between workspace and repo)



#### 版本回退 (commit id)



每次提交都会保留一个快照，在 Git 中称为`commit`

`git log`查看 Git 仓库的提交历史记录

```shell
$ git log
commit 1094adb7b9b3807259d8cb349e7df1d4d6477073 (HEAD -> master)//commit id ； HEAD 指针表示当前版本
Author: Michael Liao <askxuefeng@gmail.com>
Date:   Fri May 18 21:06:15 2018 +0800

    append GPL//提交改动时的注释
```



> Git 使用的分布式版本控制系统，因此`commit id`并不是 “0,1,2,3...”，而是一个SHA1计算出来的一个非常大的数字，用十六进制表示。当多人协同编程时 “1,2,3”的版本号方式显然会产生冲突。每提交一个新版本，实际上Git就会把它们自动串成一条**时间线**。如果使用可视化工具查看Git历史，就可以更清楚地看到提交历史的时间线



* 回到从前：

首先，Git必须知道当前版本是哪个版本，在Git中，用`HEAD`表示当前版本，也就是最新的提交`1094adb...`，上一个版本就是`HEAD^`，上上一个版本就是`HEAD^^`，当然往上100个版本写100个`^`比较容易数不过来，所以写成`HEAD~100`。

使用`git reset`指令：

```shell
$ git reset --hard HEAD^
HEAD is now at e475afc add distributed
```



之后`git log`中就不会有 reset 前的版本记录，这时候可以手动往前翻命令窗口，找到 reset 前版本的 `commit id`，**更好的方法**是`git reflog`，从中可以看到之前的每一次命令和当时的`commit id`和注释。



#### 管理修改

*Git 管理的是修改而不是文件*



修改1——添加——修改2——提交，结果是修改2没有提交。

>  每次修改，如果不用`git add`到暂存区，那就不会加入到`commit`中



#### 撤销修改

*粒度：单个文件*



`git checkout -- file`可以丢弃工作区的修改：

```shell
$ git checkout -- readme.txt
```



命令`git checkout -- readme.txt`意思就是，把`readme.txt`文件在工作区的修改全部撤销，这里有两种情况：

一种是`readme.txt`自修改后还没有被放到暂存区，现在，撤销修改就回到和**版本库**一模一样的状态；

一种是`readme.txt`已经添加到暂存区后，又作了修改，现在，撤销修改就回到**添加到暂存区后**的状态。



`git reset HEAD <file>`可以把暂存区的修改撤销掉（**unstage**），重新放回工作区，以应对第二种情况，之后`git checkout -- readme.txt`再把工作区还原回和版本库相同。



#### 删除文件



命令`git rm`用于删除一个文件。如果一个文件已经被提交到版本库，那么你永远不用担心误删，但是要小心，你只能恢复文件到最新版本，你会丢失**最近一次提交后你修改的内容**。



### 远程仓库

*远程仓库就是**远程**的**仓库**...*

GitHub 提供了 Git 仓库托管服务。由于你的本地Git仓库和GitHub仓库之间的传输是通过SSH加密的，所以，需要一点设置：

第1步：创建SSH Key。在用户主目录下的 .ssh 目录

创建SSH Key：

```shell
$ ssh-keygen -t rsa -C "youremail@example.com"
```

可以在用户主目录里找到`.ssh`目录，里面有`id_rsa`和`id_rsa.pub`两个文件，这两个就是SSH Key的**秘钥对**[^SSH]，`id_rsa`是**私钥**，不能泄露出去，`id_rsa.pub`是**公钥**，可以放心地告诉任何人。

为什么GitHub需要SSH Key呢？因为GitHub需要识别出你推送的提交确实是你推送的，而不是别人冒充的，而Git支持SSH协议，所以，GitHub只要知道了你的公钥，就可以确认只有你自己才能推送。

当然，GitHub允许你添加多个Key。假定你有若干电脑。



####  添加远程仓库（创建-关联-推送）

*显而易见的一点是需要设置 Git 和 GitHub 两者之后将他们对接*



现在的情景是，你**已经在本地创建**了一个Git仓库后，又想**在GitHub创建**一个Git仓库，并且让这两个仓库**进行远程同步**，这样，GitHub上的仓库既可以作为备份，又可以让其他人通过该仓库来协作，真是一举多得。



1. 登录 GitHub 并**创建**仓库`TheWorld`，现在这个仓库是空的，可以基于这个仓库克隆也可以把一个**已有的本地仓库**与之**关联**：在本地的`learngit`仓库下运行命令：

   ```shell
   $ git remote add origin git@github.com:michaelliao/learngit.git
   ```

   *用户名错误关联不会有问题但是会**无法推送**，因为 SSH Key 公钥不在误填的账户列表中*

   添加后，远程库的名字就是`origin`，这是Git默认的叫法，也可以改成别的，但是`origin`这个名字一看就知道是远程库。

2. 把本地库的所有内容**推送**到远程库上：

   ```shell
   $ git push -u origin master
   Counting objects: 20, done.
   Delta compression using up to 4 threads.
   Compressing objects: 100% (15/15), done.
   Writing objects: 100% (20/20), 1.64 KiB | 560.00 KiB/s, done.
   Total 20 (delta 5), reused 0 (delta 0)
   remote: Resolving deltas: 100% (5/5), done.
   To github.com:michaelliao/learngit.git
    * [new branch]      master -> master
   Branch 'master' set up to track remote branch 'master' from 'origin'.
   ```

   `git push 远程库名 分支名`	*下一节有提交到 dev 分支的内容*

   把本地库的内容推送到远程，用`git push`命令，**实际上是把当前分支`master`推送到远程。**

   由于远程库是空的，我们第一次推送`master`分支时，加上了`-u`参数，Git不但会把本地的`master`分支内容推送的远程新的`master`分支，还会把本地的`master`分支和远程的`master`分支关联起来，在以后的推送或者拉取时就可以简化命令。



##### 删除远程库

如果添加的时候地址写错了，或者就是想删除远程库，可以用`git remote rm <name>`命令。使用前，建议先用`git remote -v`查看远程库信息：

```
$ git remote -v
origin  git@github.com:michaelliao/learn-git.git (fetch)
origin  git@github.com:michaelliao/learn-git.git (push)
```

然后，根据名字删除，比如删除`origin`：

```
$ git remote rm origin
```

此处的“删除”其实是解除了本地和远程的绑定关系，并不是物理上删除了远程库。远程库本身并没有任何改动。要真正删除远程库，需要登录到GitHub，在后台页面找到删除按钮再删除。

##### 小结

要关联一个远程库，使用命令`git remote add origin git@server-name:path/repo-name.git`；

关联一个远程库时必须给远程库指定一个名字，`origin`是默认习惯命名；

关联后，使用命令`git push -u origin master`第一次推送master分支的所有内容；

此后，每次本地提交后，只要有必要，就可以使用命令`git push origin master`推送最新修改；

分布式版本系统的最大好处之一是在本地工作完全不需要考虑远程库的存在，也就是有没有联网都可以正常工作，而SVN在没有联网的时候是拒绝干活的！当有网络的时候，再把本地提交推送一下就完成了同步，真是太方便了！



#### 从远程库克隆

*噢...*



### 分支管理

*多人协作部分是 GitHub 的意义，我觉得*







## Git 工作区、暂存区和版本库



### 基本概念



* 工作区：在 git 管理下的正常目录都算是工作区。我们平时的编辑工作都是在工作区完成。
* 暂存区：stage 或 index。一般存放在`.git`目录下的 index 文件 (.git/index) 中，所以有时也称索引 (index)。
* 版本库：工作区有一个隐藏目录`.git`，这个不是工作区而是 Git 的版本库。



图中展示了工作区、版本库**中的**暂存区和版本库之间的关系：

![img](https://www.runoob.com/wp-content/uploads/2015/02/1352126739_7909.jpg)






[^分支和分支指针]:  2021/5/20
[^commit语法]: 2021/5/20待补充

## TRY



从远程仓库同步到本地 (pull)，在本地分支修改最终合并 (add commit and merge) 到本地Main分支，最后上传远程仓库 (push)



### 版本库/本地仓库(repository)



在目录中`git init`以新建本地仓库；`git clone`以从本地或远程clone建立仓库



### 工作区-暂存区(staging area)-版本库



修改文件后`git add`将改动添加到暂存区



`git commit -m '注释信息'`将**暂存区**内容更新到版本库

> 修改文件，`git add`，修改文件，`git commit`

以上的操作只能提交第一次修改





### 分支管理



```shell
$ git switch testing
//change something in the File README.md
$ git add README.md
$ git commit -m ""
$ git switch main
//发现README.md变回未修改的状态了
$ git merge testing //将testing分支内容与当前分支合并
```

More..[^ GitHub 远程分支理解和使用][^冲突]



## Git bash 文件和文件路径操作



1. `ls`列出当前目录中的内容。`ll`列出的更加详细。
2. `pwd`显示当前目录**路径**。
3. `cd`进入目录，如`cd d:\learn`进入到 D 盘的 learn 文件夹。`cd .\learn`相对路径
4. `touch`新建一个文件，如`touch README.md`
5. `mkdir`新建一个文件夹，如`mkdir src`
6. `rm`删除一个文件，`rm README.md`
7. `rm -r`删除一个文件夹，`rm -r src`
8. `mv`移动文件，如`mv index.html src`，这样写必须保证文件和目标文件夹在同一目录下，不然**应该**换成绝对路径等
9. `reset`清屏











```shell
KrednE@DESKTOP-PP2N26L MINGW64 ~/Desktop/LearnDocs (master)
$ git commit -m "first commit"
[master (root-commit) 0fcd3d0] first commit
 1 file changed, 1 insertion(+)
 create mode 100644 README.md

KrednE@DESKTOP-PP2N26L MINGW64 ~/Desktop/LearnDocs (master)
$ git branch -M main

KrednE@DESKTOP-PP2N26L MINGW64 ~/Desktop/LearnDocs (main)
$ git remote add origin git@github.com:Jeiplos/LearningDocs.git

KrednE@DESKTOP-PP2N26L MINGW64 ~/Desktop/LearnDocs (main)
$ git push -u origin main
Enumerating objects: 3, done.
Counting objects: 100% (3/3), done.
Writing objects: 100% (3/3), 226 bytes | 226.00 KiB/s, done.
Total 3 (delta 0), reused 0 (delta 0), pack-reused 0
To github.com:Jeiplos/LearningDocs.git
 * [new branch]      main -> main
Branch 'main' set up to track remote branch 'main' from 'origin'.

```



## 操作流程

*我真想摸了今天。。。只记录下流程吧。。*



1. 创建 GitHub 账号

2. 本地安装 Git ，设置 SSH，GitHub 中填写公钥

3. 在 GitHub 中创建 Repository，示例命名`learn`，创建分支 dev

4. 在本地文件夹中创建 Repository：在文件夹中运行 Git bash，`git init`会在**当前**文件夹中生成 .git 文件夹

5. 连接到远程仓库：这是 GitHub 提供的一套流程，要注意的一点是在第一次 commit 之后 `git branch`才能正常显示 branch 列表

   ```shell
   $ touch README.md	//新建README.md
   $ git add README.md	
   $ git commit -m "first commit"
   $ git branch -M main
   $ git remote add origin git@github.com:Jeiplos/LearningDocs.git	//两个关键字两个参数，在这里设置了远程仓库的名字
   $ git push -u origin main	//-u 参数将本地该分支与远程的 main 分支关联了，
   ```

   > 再来说说git push -u和git branch --set-upstream-to指令之间的区别。
   >
   > 举个例子：我要把本地分支mybranch1与远程仓库origin里的分支mybranch1建立关联。
   >
   > （如果使用下列途径1的话，首先，你要切换到mybranch1分支上（git checkout mybranch1））
   >
   > 两个途径：1. git push -u origin mybranch1  2. git branch --set-upstream-to=origin/mybranch1 mybranch1
   >
   > 这两种方式都可以达到目的。但是1方法更通用，因为你的远程库有可能并没有mybranch1分支，这种情况下你用2方法就不可行，连目标分支都不存在，怎么进行关联呢？所以可以总结一下：git push -u origin mybranch1 相当于 git push origin mybranch1 + git branch --set-upstream-to=origin/mybranch1 mybranch1
   >
   > 
   >
   > 作者：老P孩儿
   > 链接：https://www.zhihu.com/question/20019419/answer/48434769
   > 来源：知乎
   > 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

6. 本地新建 dev branch 并与 远程仓库 dev 连接：

   ```shell
   $ git switch -c dev		//新建并切换到 dev
   $ git branch
   $ git branch --set-upstream-to=origin/dev //连接，参考上方文章
   ```

   * 前面是手动创建了远程 dev 和本地 dev 分支后的连接方法。当从远程库 clone 时，默认情况下只能看到本地的 main 分支。这时就有`$ git checkout -b dev origin/dev`创建本地 dev 分支。

7. 假设远程仓库有 README.md 或者怎样，总之要拉取远程分支到本地分支：

   ```shell
   $ git switch dev
   //git pull <远程主机名> <远程分支名>:<本地分支名>
   $ git pull origin dev//远程分支名
   $ git add .
   $ git commit -m "message"
   ```

8. 把本地 dev 合并到 本地 main：

   ```shell
   $ git switch main
   $ git merge dev
   ```

9. 本地 main 推送：

   ```shell
   $ git push origin main
   //git push <远程主机名> <本地分支名>:<远程分支名>
   //如果本地分支名与远程分支名相同，则可以省略冒号：
   //git push <远程主机名> <本地分支名>
   ```

   



[^SSH]: 加密和解密是采用不同的密钥（公开密钥），也就是非对称密钥密码系统，每个通信方均需要两个密钥，即公钥和私钥，这两把密钥可以互为加解密。身份认证的过程如下：1. Alice用她的私人密钥对文件加密，从而对文件签名。2. Alice将签名的文件传送给Bob。3. Bob用Alice的公钥解密文件，从而验证签名。
[^ GitHub 远程分支理解和使用]: 
[^冲突]: 




[^分支和分支指针]:  2021/5/20
[^commit语法]: 2021/5/20待补充
