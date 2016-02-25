package org.opensjp.openbigpipe.core;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.opensjp.openbigpipe.exception.BigPipeJsPreHandleException;
/**
 * 
 * @author lepdou
 *
 */
public class SimpleBigpipeViewFrameSupport implements BigpipeViewFrameStrategy {
	private static Logger logger = Logger.getLogger(SimpleBigpipeViewFrameSupport.class);  
    private static SimpleBigpipeViewFrameSupport instance = new SimpleBigpipeViewFrameSupport();
    private SimpleBigpipeViewFrameSupport() {
    }
    public static SimpleBigpipeViewFrameSupport newInstance() {
        return instance;
    }
    private static final String bigpipeScript = "<script type=\"application/javascript\">\n" +
            "        function replace(id, content) {\n" +
            "            var pagelet = document.getElementById(id);\n" +
            "            pagelet.innerHTML = content;\n" +
            "        }\n" +
            "    </script>";

    public String execute(String html) {
        List<String> tags = Arrays.asList("</head>", "</title>");
        String result = null;
        for (String tag : tags) {
            result = build(html, tag);
            if (StringUtils.isNotEmpty(result)) {
                break;
            }
        }
        if (StringUtils.isEmpty(result)) {
            StringBuilder sb = new StringBuilder();
            for (String tag : tags)
                sb.append(tag).append(",");
            logger.debug("html must contain these" + sb.toString() + "tags");
            throw new BigPipeJsPreHandleException("Html must contain these" + sb.toString() + "tags");
        } else
            return result;
    }

    private String build(String html, String tag) {
        StringBuilder sb = new StringBuilder();
        int indexOfHeadEnd = StringUtils.indexOfIgnoreCase(html, tag);
        if (indexOfHeadEnd > 0) {
            sb.append(html.substring(0, indexOfHeadEnd))
                    .append(bigpipeScript)
                    .append("\n")
                    .append(html.substring(indexOfHeadEnd, html.length()));
        }
        return sb.toString();
    }
}
