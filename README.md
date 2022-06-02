# proxydemo
Demo Spring Boot application to use RestTemplate access Rest API through authenticated Web Proxy 

## Deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: proxydemo
spec:
  replicas: 1
  selector:
      matchLabels:
        name: proxydemo
  template:
    metadata:
      labels:
        name: proxydemo
    spec:
      containers:
      - image: nontster/proxydemo:0.0.2
        imagePullPolicy: IfNotPresent
        name: proxydemo
        terminationMessagePath: /dev/termination-log
        envFrom:
        - configMapRef:
            name: proxydemo-config
            optional: true
```

## ConfigMap
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: proxydemo-config
data:
  proxy_enabled: "true"
  proxy_host: "1.2.3.4"
  proxy_port: "8080"
  proxy_username: "myUsername"
  proxy_password: "myP@ssw0rd"
```
