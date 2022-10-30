import pika

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='183.99.87.90'))
channel = connection.channel()

channel.basic_publish(exchange='topic_logs', routing_key='key', body='Hello World!')

print(" [x] Sent 'Hello World!'")
connection.close()