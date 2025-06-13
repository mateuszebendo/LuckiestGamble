<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/components" prefix="my-c"%>
<%@ taglib uri="/WEB-INF/tlds/roletaFunctions" prefix="rol" %>

<my-c:textField id="aposta_direta" name="aposta_direta" type="number" label="Escolha um numero para a Aposta Direta" inputClass="default-input"/>
<my-c:dinamicSelect id="aposta_split" name="aposta_split" options="${rol:getSplitCombinations()}" label="Escolha uma Aposta Split"/>
<my-c:dinamicSelect id="aposta_street" name="aposta_street" options="${rol:getStreetCombinations()}" label="Escolha uma Aposta Street"/>
<my-c:dinamicSelect id="aposta_corner" name="aposta_corner" options="${rol:getCornerCombinations()}" label="Escolha uma Aposta Corner"/>
<my-c:dinamicSelect id="aposta_six_line" name="aposta_six_line" options="${rol:getSixLineCombinations()}" label="Escolha uma Aposta Six Line"/>
<my-c:dinamicSelect id="aposta_duzia" name="aposta_duzia" options="${rol:getDozens()}" label="Escolha uma Duzia"/>
<my-c:dinamicSelect id="aposta_coluna" name="aposta_coluna" options="${rol:getColumns()}" label="Escolha uma Coluna"/>
<my-c:dinamicSelect id="aposta_alto_baixo" name="aposta_alto_baixo" options="${rol:getHighLow()}" label="Escolha Alto/Baixo"/>
<my-c:dinamicSelect id="aposta_par_impar" name="aposta_par_impar" options='${["Par", "Impar"]}' label="Escolha Par/Impar"/>
<my-c:dinamicSelect id="aposta_vermelho_preto" name="aposta_vermelho_preto" options='${["Vermelho", "Preto"]}' label="Escolha Vermelho/Preto"/>