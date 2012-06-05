package pl.edu.agh.iosr.brokers;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserDaoImpl implements UserDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public User getUser(String name) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(User.class);	
		List<?> list = crit.add(Restrictions.eq("name", name)).setMaxResults(1).list();
		if (list.size() == 0)
			return null;
		return (User) list.get(0);
	}

}
