<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:f1="http://www.example.com/formula_one_circuits">

    <xsl:template match="/">
        <html>
            <head>
                <title>Formula One Circuits</title>
            </head>
            <body>
                <h1>Formula One Circuits</h1>
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Direction</th>
                        <th>Location</th>
                        <th>Country</th>
                        <th>Length (km)</th>
                        <th>Turns</th>
                        <th>Grand Prix</th>
                        <th>Seasons</th>
                        <th>Grands Prix Held</th>
                    </tr>
                    <xsl:for-each select="f1:formula_one_circuits/f1:circuit"> 
                        <tr>
                            <td><xsl:value-of select="f1:name"/></td> 
                            <td><xsl:value-of select="f1:type"/></td> 
                            <td><xsl:value-of select="f1:direction"/></td> 
                            <td><xsl:value-of select="f1:location"/></td> 
                            <td><xsl:value-of select="f1:country"/></td> 
                            <td><xsl:value-of select="f1:length"/></td> 
                            <td><xsl:value-of select="f1:turns"/></td> 
                            <td><xsl:value-of select="f1:grand_prix"/></td> 
                            <td><xsl:value-of select="f1:seasons"/></td> 
                            <td><xsl:value-of select="f1:grands_prix_held"/></td> 
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>