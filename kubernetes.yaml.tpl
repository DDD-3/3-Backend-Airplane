apiVersion: apps/v1
kind: Deployment
metadata:
  name: airplane-backend
  labels:
    app: airplane-backend
spec:
  replicas: 2
  selector:
    matchLabels:
      app: airplane-backend
  template:
    metadata:
      labels:
        app: airplane-backend
    spec:
      containers:
        - name: airplane-backend
          image: gcr.io/GOOGLE_CLOUD_PROJECT/airplane-backend:COMMIT_SHA
          ports:
            - containerPort: 8080
---
kind: Service
apiVersion: v1
metadata:
  name: airplane-backend
spec:
  selector:
    app: airplane-backend
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  type: LoadBalancer
