pipeline {
    agent any  // Exécute le pipeline sur n'importe quel agent Jenkins disponible

    tools {
        jdk 'jdk17'         // JDK configuré dans Jenkins (voir étape 3)
        maven 'Maven 3.9.6' // Maven configuré dans Jenkins
    }

    stages {
        // Étape 1 : Compilation
        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'  // Compile sans exécuter les tests
            }
        }

        // Étape 2 : Tests et rapports
        stage('Test') {
            steps {
                sh './mvnw test'                       // Exécute les tests
                junit 'target/surefire-reports/**/*.xml' // Publie les résultats des tests
                jacoco()                               // Publie la couverture de code
            }
        }

        // Étape 3 : Empaquetage et déploiement (exemple avec Nexus)
        stage('Deploy') {
            steps {
                sh './mvnw deploy -DskipTests'  // Déploie l'artefact dans Nexus
            }
        }
    }

    // Actions post-build (notifications)
    post {
        success {
            mail to: 'admin@example.com',
            subject: "Build réussi : ${currentBuild.fullDisplayName}",
            body: "Le build ${env.BUILD_URL} est réussi."
        }
        failure {
            mail to: 'admin@example.com',
            subject: "Build échoué : ${currentBuild.fullDisplayName}",
            body: "Le build ${env.BUILD_URL} a échoué."
        }
    }
}
