###引用gitee仓库jar包
maven的setting文件添加下面内容

	<profile>
		<id>jackdking</id>
		<repositories>
			<repository>
				<id>gitee</id>
				<url>https://gitee.com/wmsking1234/maven/raw/master/repository</url>
				<releases>
					<updatePolicy>always</updatePolicy>
					<enabled>true</enabled>
				</releases>
				<snapshots>
					<updatePolicy>always</updatePolicy>
					<enabled>true</enabled>
					<checksumPolicy>fail</checksumPolicy>
				</snapshots>
			</repository>
		</repositories>
	</profile>


    <activeProfiles>
        <activeProfile>alwaysActiveProfile</activeProfile>
        <activeProfile>jackdking</activeProfile>
        <activeProfile>anotherAlwaysActiveProfile</activeProfile>
    </activeProfiles>

然后在包中引入pom依赖。