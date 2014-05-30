import httplib
import random
import time
import sys
from socket import socket


def getWebPage(conn, server, webpage):
    """
	s = socket()
	s.connect((server, 80))
	s.send("POST %s HTTP/1.1\r\n" % webpage)
	s.send("Host: %s\r\n" % server)
	s.send("Content-Type: text/xml\r\n")
	#s.send("Content-Length: %d\r\n\r\n" % len(xmlmessage))
	#s.send(xmlmessage)
	#s.send()
	for line in s.makefile():
	    print line,
	s.close()
	"""
	
    conn.request("GET", webpage )

    try: 
    	r1 = conn.getresponse()

        print webpage
        print r1.status, r1.reason

        data1 = r1.read()

    except:
        print(sys.exc_info())


if len(sys.argv)>2: 

    server = sys.argv[1]

    webpages = sys.argv[2:len(sys.argv)]

    print webpages

    print "Requesting web pages\r"

    run = 1

    conn = httplib.HTTPConnection(server)

    while run ==  1 : 
        sleepTime = random.randint(3,5)
        print sleepTime
        time.sleep(sleepTime)
        i = random.randint(0,len(webpages)-1)
        getWebPage(conn, server, webpages[i])
    
    conn.close()

else: 

    print """Usage: target {URIs}"""




