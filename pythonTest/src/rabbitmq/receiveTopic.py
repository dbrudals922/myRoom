import pika
import sys

connection = pika.BlockingConnection(pika.ConnectionParameters(host='183.99.87.90'))
channel = connection.channel()

channel.exchange_declare(exchange='topic_logs', exchange_type='topic')

result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

channel.queue_bind(exchange='topic_logs',
                       queue=queue_name,
                       routing_key="key")

def callback(ch, method, properties, body):
    print(" [x] %r:%r" % (method.routing_key, body))

channel.basic_consume(
        queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()