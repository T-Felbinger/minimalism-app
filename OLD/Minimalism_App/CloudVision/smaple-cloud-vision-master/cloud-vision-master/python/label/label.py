import sys
import platform

def application(environ, start_response):
    headers = [('content-type', 'text/plain')]
    start_response('200 OK', headers)
    return[b"Hello World"]