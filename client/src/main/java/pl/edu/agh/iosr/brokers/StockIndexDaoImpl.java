package pl.edu.agh.iosr.brokers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class StockIndexDaoImpl implements StockIndexDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveStockIndex(StockIndex index) {
		sessionFactory.getCurrentSession().save(index);
	}

	@Override
	public StockIndex getLatest(String name) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(StockIndex.class);	
		return (StockIndex) crit.add(Restrictions.eq("name", name)).addOrder(Order.desc("date")).setMaxResults(1).list().get(0);
	}

	@Override
	public Set<StockIndex> getAllLatest() {
		List<String> names = sessionFactory.getCurrentSession().createQuery("select distinct name from StockIndex index").list();
		Set<StockIndex> ret = new HashSet<StockIndex>();
		for (String name : names){
			Query query = sessionFactory.getCurrentSession().createQuery("from StockIndex index where index.name = ? order by timestamp desc");
			query.setString(0, name);
			query.setMaxResults(1);
			StockIndex ind = (StockIndex) query.list().get(0);
			ret.add(ind);
		}
		return ret;
	}
}
