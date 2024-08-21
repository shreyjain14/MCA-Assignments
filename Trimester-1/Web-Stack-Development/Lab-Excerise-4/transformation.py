import lxml.etree as ET

# Load XML and XSD
xml_doc = ET.parse("formula_one_circuits.xml") 
xsd_doc = ET.parse("formula_one_circuits.xsd")
xmlschema = ET.XMLSchema(xsd_doc)

# Validate
is_valid = xmlschema.validate(xml_doc)
if not is_valid:
    print("XML data is invalid:")
    for error in xmlschema.error_log:
        print(f"  - {error.message} (Line {error.line}, Column {error.column})")
else:
    print("XML data is valid according to the schema.")

    # Proceed with transformation if valid
    xslt = ET.parse("transform.xsl")
    transform = ET.XSLT(xslt)
    newdom = transform(xml_doc)
    with open('output.html', 'wb') as f:
        f.write(ET.tostring(newdom, pretty_print=True))
    print("HTML output generated successfully!")