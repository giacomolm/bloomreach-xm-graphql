package org.example.graphql.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.beans.BaseDocument;
import org.example.beans.Blogpost;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.builder.ConstraintBuilder;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.request.HstRequestContext;

import java.util.ArrayList;
import java.util.List;

public class BlogpostFetcher implements DataFetcher<List<Blogpost>> {

    @Override
    public List<Blogpost> get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception {
        final HstRequestContext requestContext = RequestContextProvider.get();
        final HippoBean scope = requestContext.getSiteContentBaseBean();
        final HstQuery hstQuery = HstQueryBuilder.create(scope)
                .ofTypes(BaseDocument.class)
                .where(ConstraintBuilder.constraint(".").contains("blog"))
                .limit(10)
                .build();

        // execute the query
        final HstQueryResult result = hstQuery.execute();
        final HippoBeanIterator iterator = result.getHippoBeans();
        final List<Blogpost> actualList = new ArrayList<>(result.getSize());
        while (iterator.hasNext()) {
            actualList.add((Blogpost) iterator.next());
        }
        return actualList;
    }
}
