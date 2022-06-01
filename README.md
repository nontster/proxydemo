# proxydemo

## deployment

```
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
