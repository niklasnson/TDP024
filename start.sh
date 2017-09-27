#!/bin/bash
echo "starts persons-rest"
cd persons-rest
ruby app.rb &
echo "starts account-rest"
cd ../banks-rest
ruby app.rb &
cd ../logging-rest
echo "starts logging-rest"
ruby app.rb
