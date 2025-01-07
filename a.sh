find . -name '*.java' | xargs cat | \
awk -vRS='{' 'match($0,/public\s+((class|interface).+(extends|implements).+$)/,a){print a[1] "#" }' | \
awk -vRS="" '{gsub("\n"," ");print}'| awk -vRS="" '{gsub("#","\n");print}'> a.puml
