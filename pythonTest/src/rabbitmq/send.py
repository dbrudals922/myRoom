import pika

class Publisher: 
    def __init__(self): 
        self.__url = '183.99.87.90' 
        self.__port = 5672 # 기본값
        self.__vhost = '/' # 기본값
        self.__cred = pika.PlainCredentials('guest', 'guest') # 생략 가능 
        self.__queue = 'ykm'; 
        
    def main(self): 
        conn = pika.BlockingConnection(pika.ConnectionParameters(self.__url, self.__port, self.__vhost, self.__cred)) 
        chan = conn.channel() 
        chan.basic_publish(exchange = '', routing_key = self.__queue, body = '하이'.encode('utf-8')) 
        conn.close()

publisher = Publisher() 
publisher.main()
