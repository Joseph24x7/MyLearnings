# Monitoring Tools

## 1. What is Grafana?
- Grafana is a dashboard and visualization tool.
- It doesn’t store data itself, but connects to data sources (like Loki, Prometheus, Elasticsearch, etc.) to show charts, graphs, and alerts.
- Grafana can send alerts (via email, Slack, Teams, etc.) when metrics cross thresholds or issues are detected. In our case, we are using teams channel for sending alerts.

## 2. What is Elasticsearch?
- Elasticsearch is a search and analytics database.
- It is mainly used to store and search logs, events, and text data at scale.
- Tools like Kibana or Grafana connect to it to visualize and analyze logs.

## 3. What is Prometheus?
- Prometheus is a monitoring system that collects and stores time-series metrics.
- It is widely used to track CPU, memory, network usage, and application performance in Kubernetes/OpenShift.
- Grafana connects to Prometheus to create metrics dashboards and alerts.