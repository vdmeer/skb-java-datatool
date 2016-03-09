
package_name="de.vandermeer.translation.characters"
source_dir="../../../../../../../skb/data"
target_dir="../../../../../../ascii/char-translation/src/main/java/de/vandermeer/translation/characters"

./dt-cmap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Text2Latex     --target java-text-2-latex
echo ""
./dt-cmap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Text2AsciiDoc  --target java-text-2-asciidoc
echo ""
./dt-cmap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Html2Latex     --target java-html-2-latex
echo ""
./dt-cmap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Html2AsciiDoc  --target java-html-2-asciidoc
echo ""
./dt-cmap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Text2Html      --target java-text-2-html
echo ""
echo ""

package_name="de.vandermeer.translation.helements"
target_dir="../../../../../../ascii/char-translation/src/main/java/de/vandermeer/translation/helements"

./dt-hemap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Text2AsciiDoc  --target java-text2asciidoc
echo ""
./dt-hemap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Text2Html      --target java-text2html
echo ""
./dt-hemap-jtrans.sh --verbose -p ${package_name} -d ${source_dir} -o ${target_dir}/Text2Latex     --target java-text2latex
echo ""
