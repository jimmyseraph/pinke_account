pipeline {
	agent any
	stages {
		stage('build') {
			steps {
				echo 'starting building jar file'
				sh 'mvn package'
			}
		}
		stage('deploy') {
		    steps {
		        echo 'starting deploy the application'
		        sh 'jar_pid=`ps -ef | awk \'/pinke_account.jar/ && !/awk/ {print $2}\'`; if [ "$jar_pid" != "" ]; then kill -9 $jar_pid; fi'
		        sh 'cp -f target/pinke_account-0.0.1-SNAPSHOT.jar /opt/microservices/pinke_account.jar'
		        sh 'JENKINS_NODE_COOKIE=dontKillMe nohup java -Dspring.datasource.username=liudao -Dspring.datasource.password=Aa-123456 -jar /opt/microservices/pinke_account.jar -cp /opt/microservices/pinke_account.jar >> /opt/microservices/pinke_account.log 2>&1 &'
		        sh 'jar_pid=`ps -ef | awk \'/pinke_account.jar/ && !/awk/ {print $2}\'`; [ "$jar_pid" != "" ]'
		    }
		}
	}
}
