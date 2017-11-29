#!/usr/bin/env bash
curl \
  -v -H "Content-Type: plain/text" --data "<html><body>Hello World</body></html>" \
  http://localhost:8080/pdf > escoConcept.pdf
