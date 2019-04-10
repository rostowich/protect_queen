pipeline{
    agent any
    triggers{
		pollSCM('* * * * *')
	}
    stages{
        stage("Compile the source code")	{
            steps	{
            sh 'chmod --recursive a+rwx ./'
            sh "./mvnw compile"
            }
        }
        stage("Test the source code")	{
            steps	{
            sh "./mvnw test"
            }
        }
         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
            steps	{
            sh "./mvnw test jacoco:report"
            publishHTML	(target:	[
				reportDir:	'target/site/jacoco',
				reportFiles:	'index.html',
				reportName:	"Code coverage report"
			])
            sh "./mvnw clean verify"
            
            }
        }
	stage("Package the application")	{
            steps	{
            sh "./mvnw clean package -DskipTests"
            }
        }
	stage("Docker build")	{
            steps	{
	    sh "docker build -t rostowich/protect_queen -f ./Dockerfile_jenkins ."
	}
        }
	stage("Docker push to the registry")	{
	    steps	{
	    sh	"docker	push rostowich/protect_queen"
	}
	}
	stage("Deploy to the staging")	{
	    steps	{
	    sh	"docker	run -d	--rm -p	8765:8080 --name protect_queen	rostowich/protect_queen"
	}
	}
    }
}
