# Linear Chess

An ironic reversal on the Autochess game genre, allowing players to build intricate systems that compete with each other in the classic pastime.

## Deployment
### Preflight
1. Ensure you have **Java 17, nodejs, git** installed on your machine
```bash
git version
java -version
npm --version
```

2. clone this git repository to your directory of choice
```bash
cd /my/directory/of/choice
git clone https://github.com/Ivan8or/LinearChess.git
```

### Backend
1. navigate into the `LinearChess/backend/` folder
```bash
cd LinearChess/backend
```

2. use gradlew to build the java executable
```bash
./gradlew clean shadowJar
```

3. run the executable
```bash
cd build/libs/
java -jar LinearChess.jar
```

### Frontend
1. navigate into the `LinearChess/frontend/` folder
```bash
cd LinearChess/frontend
```

2. create a production build
```bash
npm run build
```

3. serve the build using node
```bash
npm install -g serve
serve -s build
```




