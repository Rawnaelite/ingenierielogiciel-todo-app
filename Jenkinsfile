pipeline {
    agent any

    tools {
        jdk 'jdk17' // Ton JDK configuré dans Jenkins
        maven 'Apache Maven 3.9.6' // Ton Maven configuré dans Jenkins
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {

        stage('Checkout') {
            steps {
               git branch: 'main', url: 'https://github.com/Rawnaelite/ingenierielogiciel-todo-app.git'


            }
        }

        stage('Build') {
            steps {
                echo "🔨 Compilation en cours..."
                sh './mvnw clean compile'
            }
        }

        stage('Tests & Couverture') {
            steps {
                echo "🧪 Lancement des tests..."
                sh './mvnw test'

                echo "📈 Publication des rapports JUnit et Jacoco"
                junit '**/target/surefire-reports/*.xml'
                jacoco execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java', inclusionPattern: '**/*.class', exclusionPattern: ''
            }
        }

        stage('Analyse statique (Checkstyle + PMD)') {
            steps {
                echo "🧹 Analyse de la qualité du code..."
                sh './mvnw checkstyle:checkstyle pmd:pmd'
                // Les résultats se trouvent dans target/site/
            }
        }

        stage('Documentation Maven Site') {
            steps {
                echo "📄 Génération de la documentation Maven..."
                sh './mvnw site'
                publishHTML(target: [
                    reportDir: 'target/site',
                    reportFiles: 'index.html',
                    reportName: 'Documentation Projet'
                ])
            }
        }

        stage('Packaging') {
            steps {
                echo "📦 Création de l'artefact .jar"
                sh './mvnw package -DskipTests'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Déploiement Nexus (si configuré)') {
            when {
                branch 'main'
            }
            steps {
                echo "🚀 Déploiement vers Nexus..."
                sh './mvnw deploy -DskipTests'
            }
        }
    }
/*

    post {
        success {
            echo "✅ Build réussi !"
            mail to: 'admin@example.com',
                 subject: "✅ Build Réussi: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Le build s'est bien déroulé !\n\nVoir: ${env.BUILD_URL}"
        }
        failure {
            echo "❌ Build échoué !"
            mail to: 'admin@example.com',
                 subject: "❌ Build Échoué: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Erreur dans le build !\n\nConsulter: ${env.BUILD_URL}"
        }
    }

*/
}
